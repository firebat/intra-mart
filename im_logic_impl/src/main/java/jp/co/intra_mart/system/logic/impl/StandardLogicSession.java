package jp.co.intra_mart.system.logic.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.*;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingContext;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingDefinition;
import jp.co.intra_mart.foundation.logic.element.*;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;
import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;
import jp.co.intra_mart.foundation.logic.exception.MappingException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import jp.co.intra_mart.foundation.logic.repository.ContextDataRepository;
import jp.co.intra_mart.system.logic.data.mapping.LogicFlowMappingContext;
import jp.co.intra_mart.system.logic.data.mapping.StandardMapper;
import jp.co.intra_mart.system.logic.element.category.ElementContextImpl;
import jp.co.intra_mart.system.logic.factory.LogicFlowElementFactory;
import jp.co.intra_mart.system.logic.flow.ExecuteTaskResult;
import jp.co.intra_mart.system.logic.flow.impl.LogicHandleEventImpl;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class StandardLogicSession implements LogicSession {

    private final Logger logger = LoggerFactory.getLogger(StandardLogicSession.class);
    private final LogicFlow flow;
    private final Map<String, Object> sessionData = Maps.newHashMap();
    private final Map<String, FlowElement<? extends Metadata>> elements = Maps.newHashMap();

    @Getter
    @Setter
    private LogicFlowElementFactory elementFactory;

    @Getter
    @Setter
    private Collection<LogicHandle> logicHandles;

    @Getter
    @Setter
    private int executionThreshold = 100;

    private boolean completed;

    public StandardLogicSession(LogicFlow flow, LogicFlowElementFactory elementFactory) {
        this.flow = flow;
        this.elementFactory = elementFactory;

        setContextData();

        if (flow.getDefinition().getConstants() != null) {
            setSessionData(CONSTANT_DATA_FIELD_NAME, flow.getDefinition().getConstants().getData());
        }

        setSessionData(INPUT_DATA_FIELD_NAME, null);
        setSessionData(VARIABLE_DATA_FIELD_NAME, null);
    }

    private void setContextData() {
        ContextDataRepository repository = LogicServiceProvider.getInstance().getContextDataRepository();
        for (Map.Entry<String, Object> entry : repository.createContextData(flow).entrySet()) {
            setSessionData(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void setSessionData(String key, Object value) {
        this.sessionData.put(key, value);
    }

    @Override
    public Map<String, Object> getSessionData() {
        return Collections.unmodifiableMap(sessionData);
    }

    @Override
    public LogicFlow getLogicFlow() {
        return flow;
    }

    @Override
    public void addLogicHandle(LogicHandle handle) {
        if (logicHandles == null) {
            logicHandles = new HashSet<>();
        }

        if (!logicHandles.contains(handle)) {
            logicHandles.add(handle);
        }
    }

    @Override
    public Object execute(Object inputData) throws LogicServiceException {

        // TODO transaction controller
        if (inputData != null) {
            setSessionData(INPUT_DATA_FIELD_NAME, inputData);
        }

        Object entryPoint = getSessionData().get(ENTRYPOINT_FIELD_NAME);
        if (entryPoint == null) {
            executeHandle(LogicHandleEventType.BEGIN_FLOW, null, getSessionData().get(INPUT_DATA_FIELD_NAME));
        }

        try {
            FlowElement<? extends Metadata> element = getStartElement();

            while (element != null) {
                if (element instanceof Executable) {
                    executeElement(element);
                }

                FlowElement<? extends Metadata> nextElement = getNextElement(element);
                if (nextElement == null) {
                    LogicFlowElementDefinition definition = flow.getFlowElementDefinition(element.getExecuteId());
                    Object result = getExecuteResult(definition.getMappingDefinition());
                    setSessionData(OUTPUT_DATA_FIELD_NAME, result);
                    executeHandle(LogicHandleEventType.END_FLOW, null, getSessionData().get(OUTPUT_DATA_FIELD_NAME));
                    this.completed = true;
                }
                element = nextElement;
            }
        } catch (LogicServiceException e) {
            this.completed = true;
            throw e;
        }

        return getSessionData().get(OUTPUT_DATA_FIELD_NAME);
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void destroy() {
    }

    private FlowElement<? extends Metadata> getStartElement() throws LogicServiceException {
        String entryPoint = (String) getSessionData().get(ENTRYPOINT_FIELD_NAME);

        if (!Strings.isNullOrEmpty(entryPoint)) {
            setSessionData(ENTRYPOINT_FIELD_NAME, null);
            LogicFlowElementDefinition definition = flow.getFlowElementDefinition(entryPoint);
            if (definition == null) {
                throw new LogicServiceException("Unknown flow element, elementId=" + entryPoint);
            }
            return getFlowElement(definition);
        }

        Collection<LogicFlowElementDefinition> definitions = flow.getNextElementDefinitions(null);
        if (definitions == null || definitions.isEmpty()) {
            throw new LogicServiceException("Entry point element not specified.");
        }
        if (definitions.size() > 1) {
            throw new LogicServiceException("Multiple entry point element specified.");
        }

        return getFlowElement(definitions.iterator().next());
    }

    private FlowElement<? extends Metadata> getFlowElement(LogicFlowElementDefinition definition) throws LogicServiceException {
        // 创建流程元素
        String executeId = definition.getExecuteId();
        if (!elements.containsKey(executeId)) {
            ElementContext elementContext = new ElementContextImpl(definition, this);
            FlowElement<? extends Metadata> element = elementFactory.createElement(elementContext);
            elements.put(executeId, element);
        }

        return elements.get(executeId);
    }

    private FlowElement<? extends Metadata> getNextElement(FlowElement<? extends Metadata> element) throws LogicServiceException {
        LogicFlowElementDefinition next = null;
        if (element instanceof Controllable) {
            String nextElementId = ((Controllable) element).execute(this);
            if (nextElementId != null) {
                next = flow.getFlowElementDefinition(nextElementId);
            }
        } else {
            Collection<LogicFlowElementDefinition> definitions = flow.getNextElementDefinitions(element.getExecuteId());
            if (definitions != null && !definitions.isEmpty()) {
                next = definitions.iterator().next();
            }
        }

        return next == null ? null : getFlowElement(next);
    }

    private void executeElement(FlowElement<? extends Metadata> element) throws LogicServiceException {

        LogicFlowElementDefinition definition = flow.getFlowElementDefinition(element.getExecuteId());

        Object inputData;
        try {
            Metadata metadata = flow.getElementMetadataByAlias(element.getAlias());
            StandardMapper mapper = new StandardMapper(definition.getMappingDefinition());
            MappingContext context = new LogicFlowMappingContext(flow, getSessionData(), metadata.getInputDataDefinition());
            inputData = mapper.map(context);
        } catch (MappingException e) {
            executeHandle(LogicHandleEventType.MAPPING_ERROR, element, e);
            if (element instanceof Task && ((Task) element).isContinueOnError()) {
                setSessionData(EXECUTE_TASK_RESULT_FIELD_NAME, new ExecuteTaskResult(element.getExecuteId(), true, e.getMessage()));
                return;
            }
            throw new FlowExecutionException("Mapping error", e);
        }

        Executable executable = (Executable) element;
        try {
            executable.preProcessing();
            executeHandle(LogicHandleEventType.BEFORE_EXECUTION, element, inputData);

            Object result = executable.execute(inputData);

            executeHandle(LogicHandleEventType.AFTER_EXECUTION, element, result);
            executable.postProcessing();

            setSessionData(element.getAlias(), result);
            setSessionData(EXECUTE_TASK_RESULT_FIELD_NAME, new ExecuteTaskResult(element.getExecuteId(), false, null));
        } catch (Throwable e) {
            executeHandle(LogicHandleEventType.EXECUTION_ERROR, element, e);

            if (e instanceof Exception && element instanceof Task && ((Task) element).isContinueOnError()) {
                setSessionData(element.getAlias(), null);
                setSessionData(EXECUTE_TASK_RESULT_FIELD_NAME, new ExecuteTaskResult(element.getExecuteId(), true, e.getMessage()));
                return;
            }

            throw e;
        }
    }

    private void executeHandle(LogicHandleEventType type, FlowElement<? extends Metadata> element, Object data) {
        if (logicHandles == null || logicHandles.isEmpty()) {
            return;
        }

        LogicFlowElementDefinition definition = element == null ? null : flow.getFlowElementDefinition(element.getExecuteId());

        LogicHandleEvent event = new LogicHandleEventImpl(this, type, definition, data);
        for (LogicHandle handle : logicHandles) {
            handle.execute(event);
        }
    }

    private Object getExecuteResult(MappingDefinition mappingDefinition) throws LogicServiceException {
        StandardMapper mapper = new StandardMapper(mappingDefinition);
        LogicFlowMappingContext context = new LogicFlowMappingContext(flow, getSessionData(), flow.getDefinition().getOutputDataDefinition());

        try {
            return mapper.map(context);
        } catch (MappingException e) {
            executeHandle(LogicHandleEventType.MAPPING_ERROR, null, e);
            throw new FlowExecutionException("mapping error", e);
        }
    }
}

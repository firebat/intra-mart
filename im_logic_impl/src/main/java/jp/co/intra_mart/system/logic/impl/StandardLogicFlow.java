package jp.co.intra_mart.system.logic.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.LogicServiceProvider;
import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.base.SequenceFlowMetadata;
import jp.co.intra_mart.foundation.logic.element.base.StartElement;
import jp.co.intra_mart.foundation.logic.element.metadata.ApplicationElementKey;
import jp.co.intra_mart.foundation.logic.element.metadata.ElementKey;
import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import jp.co.intra_mart.foundation.logic.repository.ElementMetadataRepository;

import java.util.Collection;
import java.util.Map;

public class StandardLogicFlow implements LogicFlow {

    private static final String PROPERTY_NAME_START_POINT = "startPoint";
    private static final String PROPERTY_NAME_END_POINT = "endPoint";

    private final LogicFlowDefinition definition;
    private Map<String, LogicFlowElementDefinition> elementMap = Maps.newHashMap();
    private Map<String, Collection<LogicFlowElementDefinition>> resolutionMap = Maps.newHashMap();
    private Map<String, Collection<LogicFlowElementDefinition>> reverseMap = Maps.newHashMap();
    private Map<String, LogicFlowElementDefinition> aliasMap = Maps.newHashMap();
    private Map<String, Metadata> metadataMap = Maps.newHashMap();

    public StandardLogicFlow(LogicFlowDefinition definition) {
        this.definition = definition;

        if (definition.getFlowElements() == null) {
            return;
        }

        for (LogicFlowElementDefinition elementDefinition : definition.getFlowElements()) {
            if (isSequence(elementDefinition)) {
                String startPoint = elementDefinition.getProperty(PROPERTY_NAME_START_POINT);
                if (!resolutionMap.containsKey(startPoint)) {
                    resolutionMap.put(startPoint, Lists.newArrayList());
                }
                resolutionMap.get(startPoint).add(elementDefinition);

                String endPoint = elementDefinition.getProperty(PROPERTY_NAME_END_POINT);
                if (!reverseMap.containsKey(endPoint)) {
                    reverseMap.put(endPoint, Lists.newArrayList());
                }
                reverseMap.get(endPoint).add(elementDefinition);
            }
            elementMap.put(elementDefinition.getExecuteId(), elementDefinition);
            aliasMap.put(elementDefinition.getAlias(), elementDefinition);
        }
    }

    @Override
    public String getFlowId() {
        return definition.getFlowId();
    }

    @Override
    public LogicFlowDefinition getDefinition() {
        return definition;
    }

    @Override
    public LogicFlowElementDefinition getFlowElementDefinition(String executeId) {
        return elementMap.get(executeId);
    }

    @Override
    public Collection<LogicFlowElementDefinition> getNextElementDefinitions(String executeId) {

        LogicFlowElementDefinition definition = executeId == null ? getStartElementDefinition() : elementMap.get(executeId);
        if (definition == null) {
            return null;
        }

        if (isSequence(definition)) {
            String nextElementId = definition.getProperty(PROPERTY_NAME_END_POINT);
            LogicFlowElementDefinition nextElement = elementMap.get(nextElementId);
            return nextElement == null ? null : Lists.newArrayList(nextElement);
        }

        return resolutionMap.get(definition.getExecuteId());
    }

    @Override
    public Collection<LogicFlowElementDefinition> getPrevElementDefinitions(String executeId) {
        LogicFlowElementDefinition elementDefinition = elementMap.get(executeId);
        if (elementDefinition == null) {
            return null;
        }

        if (isSequence(elementDefinition)) {
            String prevElementId = elementDefinition.getProperty(PROPERTY_NAME_START_POINT);
            LogicFlowElementDefinition prevElement = elementMap.get(prevElementId);
            return prevElement == null ? null : Lists.newArrayList(prevElement);
        }
        return reverseMap.get(elementDefinition.getExecuteId());
    }

    @Override
    public Metadata getElementMetadataByAlias(String alias) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(alias), "alias is not specified.");
        if (metadataMap.containsKey(alias)) {
            return metadataMap.get(alias);
        }

        LogicFlowElementDefinition definition = aliasMap.get(alias);
        if (definition == null) {
            return null;
        }

        ElementMetadataRepository metadataRepository = LogicServiceProvider.getInstance().getElementMetadataRepository();
        return metadataRepository.getMetadata(definition.getKey());
    }

    @Override
    public boolean isTransaction() {
        return definition.isTransaction();
    }

    private boolean isSequence(LogicFlowElementDefinition definition) {
        ElementKey key = definition.getKey();
        if (!(key instanceof ApplicationElementKey)) {
            return false;
        }

        ElementMetadataRepository repository = LogicServiceProvider.getInstance().getElementMetadataRepository();
        Metadata metadata = repository.getMetadata(key);
        return metadata instanceof SequenceFlowMetadata;
    }

    private LogicFlowElementDefinition getStartElementDefinition() {
        String elementId = StartElement.class.getAnnotation(LogicFlowElement.class).id();
        ApplicationElementKey key = new ApplicationElementKey(elementId);
        for (LogicFlowElementDefinition definition : elementMap.values()) {
            if (definition.getKey().equals(key)) {
                return definition;
            }
        }
        return null;
    }
}

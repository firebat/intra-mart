package jp.co.intra_mart.system.logic.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.LogicRuntime;
import jp.co.intra_mart.foundation.logic.LogicServiceProvider;
import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;
import jp.co.intra_mart.foundation.logic.exception.ObjectNotFoundException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowErrorObject;
import jp.co.intra_mart.foundation.logic.flow.debug.LogicSessionDebugger;
import jp.co.intra_mart.system.logic.factory.LogicFlowElementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class StandardLogicRuntime implements LogicRuntime {

    private static final String DEBUG_LOGGER_NAME = "LOGIC_FLOW_LOG";
    private LogicFlowElementFactory logicFlowElementFactory;

    public StandardLogicRuntime() {}

    public StandardLogicRuntime(LogicFlowElementFactory logicFlowElementFactory) {
        this.logicFlowElementFactory = logicFlowElementFactory;
    }

    @Override
    public LogicSession createSession(String flowId) throws LogicServiceException {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(flowId), "flowId is not specified.");

        LogicServiceProvider provider = LogicServiceProvider.getInstance();
        LogicFlowDefinition definition = provider.getLogicFlowRepository().getLogicFlow(flowId);
        if (definition == null) {
            throw new ObjectNotFoundException(flowId);
        }

        return createSession(definition);
    }

    @Override
    public LogicSession createSession(String flowId, int version) throws LogicServiceException {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(flowId), "flowId is not specified.");
        Preconditions.checkArgument(version > 0, "version is invalid.");

        LogicServiceProvider provider = LogicServiceProvider.getInstance();
        LogicFlowDefinition definition = provider.getLogicFlowRepository().getLogicFlow(flowId, version);
        if (definition == null) {
            throw new ObjectNotFoundException(flowId);
        }

        return createSession(definition);
    }

    @Override
    public LogicSession createSession(LogicFlowDefinition definition) throws LogicServiceException {

        Preconditions.checkNotNull(definition, "definition is not specified.");

        LogicFlow flow = new StandardLogicFlow(definition);
        LogicSession session = new StandardLogicSession(flow, logicFlowElementFactory);

        Logger logger = LoggerFactory.getLogger(DEBUG_LOGGER_NAME);
        session.addLogicHandle(new LogicSessionDebugger(definition.getFlowId(), logger));

        return session;
    }

    @Override
    public Collection<LogicFlowErrorObject> validate(LogicFlowDefinition definition) {
        Preconditions.checkNotNull(definition, "definition is not specified.");
        return null;
    }
}

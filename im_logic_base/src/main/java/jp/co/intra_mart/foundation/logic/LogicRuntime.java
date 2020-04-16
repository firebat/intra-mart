package jp.co.intra_mart.foundation.logic;

import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowErrorObject;

import java.util.Collection;

public interface LogicRuntime {

    Collection<LogicFlowErrorObject> validate(LogicFlowDefinition definition);

    LogicSession createSession(String flowId) throws LogicServiceException;

    LogicSession createSession(String flowId, int version) throws LogicServiceException;

    LogicSession createSession(LogicFlowDefinition definition) throws LogicServiceException;
}

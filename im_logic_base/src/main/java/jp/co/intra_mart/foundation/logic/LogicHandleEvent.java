package jp.co.intra_mart.foundation.logic;

import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;

public interface LogicHandleEvent {

    LogicSession getLogicSession();

    LogicHandleEventType getEventType();

    Object getData();

    LogicFlowElementDefinition getElementDefinition();
}

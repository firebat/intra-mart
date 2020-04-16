package jp.co.intra_mart.system.logic.flow.impl;

import jp.co.intra_mart.foundation.logic.LogicHandleEvent;
import jp.co.intra_mart.foundation.logic.LogicHandleEventType;
import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogicHandleEventImpl implements LogicHandleEvent {

    private final LogicSession logicSession;
    private final LogicHandleEventType eventType;
    private final LogicFlowElementDefinition elementDefinition;
    private final Object data;
}

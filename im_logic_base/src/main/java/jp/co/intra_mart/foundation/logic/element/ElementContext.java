package jp.co.intra_mart.foundation.logic.element;

import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;

public interface ElementContext {

    LogicFlowElementDefinition getElementDefinition();

    LogicSession getLogicSession();

    void addFlowElementCloser(FlowElementCloser closer);
}

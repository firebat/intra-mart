package jp.co.intra_mart.system.logic.element.category;

import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.FlowElementCloser;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;

public class ElementContextImpl implements ElementContext {

    private final LogicFlowElementDefinition elementDefinition;
    private final LogicSession logicSession;

    public ElementContextImpl(LogicFlowElementDefinition elementDefinition, LogicSession logicSession) {
        this.elementDefinition = elementDefinition;
        this.logicSession = logicSession;
    }

    @Override
    public LogicFlowElementDefinition getElementDefinition() {
        return elementDefinition;
    }

    @Override
    public LogicSession getLogicSession() {
        return logicSession;
    }

    @Override
    public void addFlowElementCloser(FlowElementCloser closer) {
        // TODO
    }
}

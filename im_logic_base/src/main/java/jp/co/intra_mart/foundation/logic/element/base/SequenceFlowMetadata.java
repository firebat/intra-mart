package jp.co.intra_mart.foundation.logic.element.base;

import jp.co.intra_mart.foundation.logic.element.metadata.FlowElementMetadata;

public class SequenceFlowMetadata extends FlowElementMetadata {

    public SequenceFlowMetadata() {
        super(SequenceFlow.class);
    }

    @Override
    public String getElementName() {
        return "LOGIC_ELEMENT_NAME_SEQUENCE_FLOW";
    }
}

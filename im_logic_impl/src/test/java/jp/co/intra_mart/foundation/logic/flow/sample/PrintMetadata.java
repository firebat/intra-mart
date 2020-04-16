package jp.co.intra_mart.foundation.logic.flow.sample;

import jp.co.intra_mart.foundation.logic.element.metadata.FlowElementMetadata;

public class PrintMetadata extends FlowElementMetadata {

    public PrintMetadata() {
        super(PrintTask.class);
    }

    @Override
    public String getElementName() {
        return "LOGIC_ELEMENT_PRINT";
    }
}

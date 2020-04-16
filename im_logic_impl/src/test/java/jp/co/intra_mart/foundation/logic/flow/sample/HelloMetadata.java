package jp.co.intra_mart.foundation.logic.flow.sample;

import jp.co.intra_mart.foundation.logic.element.metadata.FlowElementMetadata;

public class HelloMetadata extends FlowElementMetadata {

    public HelloMetadata() {
        super(HelloTask.class);
    }

    @Override
    public String getElementName() {
        return "LOGIC_ELEMENT_HELLO";
    }
}

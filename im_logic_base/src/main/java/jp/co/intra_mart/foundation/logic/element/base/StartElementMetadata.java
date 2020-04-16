package jp.co.intra_mart.foundation.logic.element.base;

import jp.co.intra_mart.foundation.logic.element.metadata.FlowElementMetadata;

public class StartElementMetadata extends FlowElementMetadata {

    public StartElementMetadata() {
        super(StartElement.class);
    }

    @Override
    public String getElementName() {
        return "LOGIC_ELEMENT_NAME_START_ELEMENT";
    }
}

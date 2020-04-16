package jp.co.intra_mart.foundation.logic.element.base;

import jp.co.intra_mart.foundation.logic.element.metadata.FlowElementMetadata;

public class EndElementMetadata extends FlowElementMetadata {

    public EndElementMetadata() {
        super(EndElement.class);
    }

    @Override
    public String getElementName() {
        return "LOGIC_ELEMENT_NAME_END_ELEMENT";
    }
}

package jp.co.intra_mart.foundation.logic.element;

import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import lombok.Getter;
import lombok.Setter;


public abstract class FlowElement<T extends Metadata> {

    protected final ElementContext context;

    @Setter
    @Getter
    private String alias;

    protected FlowElement(ElementContext context) {
        this.context = context;
        this.alias = context.getElementDefinition().getAlias();
    }

    public String getExecuteId() {
        return this.context.getElementDefinition().getExecuteId();
    }
}

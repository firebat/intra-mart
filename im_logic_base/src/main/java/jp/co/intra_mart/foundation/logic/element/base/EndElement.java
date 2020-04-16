package jp.co.intra_mart.foundation.logic.element.base;

import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.Controllable;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.category.BaseElements;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;

@LogicFlowElement(id="im_end", category = BaseElements.class, index = 2)
public class EndElement extends FlowElement<EndElementMetadata> implements Controllable {

    public EndElement(ElementContext context) {
        super(context);
    }

    @Override
    public String execute(LogicSession session) throws FlowExecutionException {
        return null;
    }
}

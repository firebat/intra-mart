package jp.co.intra_mart.foundation.logic.element.base;

import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.annotation.IgnoreSetting;
import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.Controllable;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.category.BaseElements;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@LogicFlowElement(id = "im_sequence", category = BaseElements.class)
public class SequenceFlow extends FlowElement<SequenceFlowMetadata> implements Controllable {

    @IgnoreSetting
    private String startPoint;

    @IgnoreSetting
    private String endPoint;

    private String condition;

    public SequenceFlow(ElementContext context) {
        super(context);
    }

    @Override
    public String execute(LogicSession session) throws FlowExecutionException {
        LogicFlowElementDefinition definition = session.getLogicFlow().getFlowElementDefinition(endPoint);
        if (definition == null) {
            throw new FlowExecutionException("Unknown endpoint element definition, id=" + endPoint);
        }
        return definition.getExecuteId();
    }
}

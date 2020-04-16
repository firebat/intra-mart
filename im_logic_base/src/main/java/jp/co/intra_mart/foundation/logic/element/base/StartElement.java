package jp.co.intra_mart.foundation.logic.element.base;

import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.Controllable;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.FlowElement;
import jp.co.intra_mart.foundation.logic.element.category.BaseElements;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;

import java.util.Collection;


@LogicFlowElement(id = "im_start", category = BaseElements.class, index = 1)
public class StartElement extends FlowElement<StartElementMetadata> implements Controllable {

    public StartElement(ElementContext context) {
        super(context);
    }

    @Override
    public String execute(LogicSession session) throws FlowExecutionException {
        LogicFlow flow = session.getLogicFlow();
        Collection<LogicFlowElementDefinition> definitions = flow.getNextElementDefinitions(getExecuteId());
        if (definitions == null || definitions.isEmpty()) {
            throw new FlowExecutionException("Start element need next elements. executeId=" + getExecuteId());
        }
        return definitions.iterator().next().getExecuteId();
    }
}

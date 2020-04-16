package jp.co.intra_mart.foundation.logic.flow.sample;

import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.Task;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;

import java.util.Map;

@LogicFlowElement(id = "sample_print", category = SampleElements.class)
public class PrintTask extends Task<PrintMetadata, Map, Map> {

    public PrintTask(ElementContext context) {
        super(context);
    }

    @Override
    public Map execute(Map data) throws FlowExecutionException {
        System.out.println(data);
        return data;
    }
}

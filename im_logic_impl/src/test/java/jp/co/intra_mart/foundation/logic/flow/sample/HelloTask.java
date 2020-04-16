package jp.co.intra_mart.foundation.logic.flow.sample;

import jp.co.intra_mart.foundation.logic.annotation.LogicFlowElement;
import jp.co.intra_mart.foundation.logic.element.ElementContext;
import jp.co.intra_mart.foundation.logic.element.Task;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;

@LogicFlowElement(id = "sample_hello", category = SampleElements.class)
public class HelloTask extends Task<HelloMetadata, HelloInput, HelloOutput> {
    public HelloTask(ElementContext context) {
        super(context);
    }

    @Override
    public HelloOutput execute(HelloInput data) throws FlowExecutionException {

        String message = "hello " + data.getName() + " from " + data.getCountry();

        HelloOutput output = new HelloOutput();
        output.setMessage(message);
        return output;
    }
}

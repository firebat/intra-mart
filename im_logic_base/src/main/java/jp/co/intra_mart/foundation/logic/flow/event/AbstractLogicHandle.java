package jp.co.intra_mart.foundation.logic.flow.event;

import jp.co.intra_mart.foundation.logic.LogicHandle;
import jp.co.intra_mart.foundation.logic.LogicHandleEvent;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;

public abstract class AbstractLogicHandle implements LogicHandle {

    private final boolean inheritable;
    private LogicHandleEvent event;

    protected AbstractLogicHandle(boolean inheritable) {
        this.inheritable = inheritable;
    }

    @Override
    public boolean isInheritable() {
        return inheritable;
    }

    @Override
    public void execute(LogicHandleEvent event) {
        this.event = event;
        LogicFlowElementDefinition definition = event.getElementDefinition();

        switch (event.getEventType()) {
            case BEGIN_FLOW:
                beginFlow(event.getData());
                break;
            case BEFORE_EXECUTION:
                beforeExecution(definition, event.getData());
                break;
            case AFTER_EXECUTION:
                afterExecution(definition, event.getData());
                break;
            case END_FLOW:
                endFlow(event.getData());
                break;
            case LOOP:
                loopEvent(definition, (LoopEventData) event.getData());
                break;
            case LOOP_END:
                loopEndEvent(definition);
                break;
            case EXCLUSIVE_GATEWAY:
                exclusiveGatewayEvent(definition, (ExclusiveGatewayEventData) event.getData());
                break;
            case EXECUTION_ERROR:
                if (definition == null) {
                    executionError((Throwable) event.getData());
                } else {
                    executionError(definition, (Throwable) event.getData());
                }
                break;
            case MAPPING_ERROR:
                if (definition == null) {
                    mappingError((Throwable) event.getData());
                } else {
                    mappingError(definition, (Throwable) event.getData());
                }
                break;
            case EXECUTION_INFORMATION:
                executionInformation(definition, (String) event.getData());
                break;
            case EXECUTION_WARNING:
                executionWarning(definition, (String) event.getData());
                break;
        }

        this.event = null;
    }

    public LogicHandleEvent getEvent() {
        return event;
    }

    protected void beginFlow(Object data) {}

    protected void beforeExecution(LogicFlowElementDefinition definition, Object data) {}

    protected void afterExecution(LogicFlowElementDefinition definition, Object data) {}

    protected void endFlow(Object data) {}

    protected void loopEvent(LogicFlowElementDefinition definition, LoopEventData data) {}

    protected void loopEndEvent(LogicFlowElementDefinition definition) {}

    protected void exclusiveGatewayEvent(LogicFlowElementDefinition definition, ExclusiveGatewayEventData data) {}

    protected void executionError(Throwable throwable) {}

    protected void executionError(LogicFlowElementDefinition definition, Throwable throwable) {}

    protected void mappingError(Throwable throwable) {}

    protected void mappingError(LogicFlowElementDefinition definition, Throwable throwable) {}

    protected void executionInformation(LogicFlowElementDefinition definition, String message) {}

    protected void executionWarning(LogicFlowElementDefinition definition, String message) {}
}

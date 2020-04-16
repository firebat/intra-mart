package jp.co.intra_mart.foundation.logic.exception;

public class FlowExecutionException extends LogicServiceException {

    public FlowExecutionException() {}

    public FlowExecutionException(Throwable cause) {
        super(cause);
    }

    public FlowExecutionException(String message) {
        super(message);
    }

    public FlowExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package jp.co.intra_mart.foundation.logic.exception;

public class LogicServiceException extends Exception {

    public LogicServiceException() {}

    public LogicServiceException(Throwable cause) {
        super(cause);
    }

    public LogicServiceException(String message) {
        super(message);
    }

    public LogicServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

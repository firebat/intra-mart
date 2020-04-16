package jp.co.intra_mart.foundation.logic.exception;

public class MappingException extends Exception {

    public MappingException() {
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}

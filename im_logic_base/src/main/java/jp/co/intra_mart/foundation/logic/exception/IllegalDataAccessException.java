package jp.co.intra_mart.foundation.logic.exception;

public class IllegalDataAccessException extends MappingException {

    public IllegalDataAccessException() {
    }

    public IllegalDataAccessException(String message) {
        super(message);
    }

    public IllegalDataAccessException(Throwable cause) {
        super(cause);
    }

    public IllegalDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

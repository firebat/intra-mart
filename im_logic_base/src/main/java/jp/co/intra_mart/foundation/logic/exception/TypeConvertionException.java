package jp.co.intra_mart.foundation.logic.exception;

public class TypeConvertionException extends MappingException {

    public TypeConvertionException() {
    }

    public TypeConvertionException(String message) {
        super(message);
    }

    public TypeConvertionException(Throwable cause) {
        super(cause);
    }

    public TypeConvertionException(String message, Throwable cause) {
        super(message, cause);
    }
}

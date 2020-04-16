package jp.co.intra_mart.foundation.logic.exception;

public class ObjectNotFoundException extends LogicServiceException {

    public ObjectNotFoundException() {}

    public ObjectNotFoundException(String message) {
        super(message);
    }
}

package jp.co.intra_mart.foundation.logic.expression;

public class EvaluateException extends Exception {

    public EvaluateException() {
    }

    public EvaluateException(String message) {
        super(message);
    }

    public EvaluateException(Throwable cause) {
        super(cause);
    }

    public EvaluateException(String message, Throwable cause) {
        super(message, cause);
    }
}

package jp.co.intra_mart.foundation.logic.element;

import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;

public interface Executable<D, R> {

    void preProcessing() throws FlowExecutionException;

    R execute(D data) throws FlowExecutionException;

    void postProcessing() throws FlowExecutionException;
}

package jp.co.intra_mart.foundation.logic.element;

import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;

/**
 * 处理任务抽象
 *
 * @param <T> 元信息类型
 * @param <D> 参数类型
 * @param <R> 返回类型
 */
public abstract class Task<T extends Metadata, D, R> extends FlowElement<T> implements Executable<D, R> {

    private boolean continueOnError;

    protected Task(ElementContext context) {
        super(context);
    }

    @Override
    public void preProcessing() throws FlowExecutionException {}

    @Override
    public void postProcessing() throws FlowExecutionException {}

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public boolean isContinueOnError() {
        return continueOnError;
    }
}

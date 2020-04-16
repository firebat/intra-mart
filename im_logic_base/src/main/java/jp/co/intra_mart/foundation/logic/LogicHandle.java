package jp.co.intra_mart.foundation.logic;

/**
 * 流程执行过程中的事件处理
 */
public interface LogicHandle {

    boolean isInheritable();

    void execute(LogicHandleEvent event);
}

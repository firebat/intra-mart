package jp.co.intra_mart.foundation.logic.element;

import jp.co.intra_mart.foundation.logic.LogicSession;
import jp.co.intra_mart.foundation.logic.exception.FlowExecutionException;

public interface Controllable {

    String execute(LogicSession session) throws FlowExecutionException;
}

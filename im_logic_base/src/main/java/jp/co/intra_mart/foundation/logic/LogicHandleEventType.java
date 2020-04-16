package jp.co.intra_mart.foundation.logic;

public enum LogicHandleEventType {

    BEGIN_FLOW,

    BEFORE_EXECUTION,

    AFTER_EXECUTION,

    END_FLOW,

    LOOP,

    LOOP_END,

    EXCLUSIVE_GATEWAY,

    EXECUTION_ERROR,

    MAPPING_ERROR,

    EXECUTION_INFORMATION,

    EXECUTION_WARNING,
}

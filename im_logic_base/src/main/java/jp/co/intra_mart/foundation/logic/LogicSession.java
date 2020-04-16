package jp.co.intra_mart.foundation.logic;

import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;

import java.util.Collection;
import java.util.Map;

public interface LogicSession {

    String CONSTANT_DATA_FIELD_NAME = "$const";
    String VARIABLE_DATA_FIELD_NAME = "$variable";
    String INPUT_DATA_FIELD_NAME = "$input";
    String OUTPUT_DATA_FIELD_NAME = "$output";
    String ACCOUNT_CONTEXT_FIELD_NAME = "$account_context";
    String USER_CONTEXT_FIELD_NAME = "$user_context";
    String EXTERNAL_USER_CONTEXT_FIELD_NAME = "$external_user_context";
    String SESSION_PROPERTIES_CONTEXT_FIELD_NAME = "$session_properties";
    String EXECUTE_TASK_RESULT_FIELD_NAME = "$task_result";
    String BREAKPOINT_FIELD_NAME = "$breakpoint";
    String ENTRYPOINT_FIELD_NAME = "$entrypoint";

    void setExecutionThreshold(int executionThreshold);

    int getExecutionThreshold();

    void setSessionData(String key, Object value);

    Map<String, Object> getSessionData();

    LogicFlow getLogicFlow();

    Collection<LogicHandle> getLogicHandles();

    void setLogicHandles(Collection<LogicHandle> logicHandles);

    void addLogicHandle(LogicHandle logicHandle);

    Object execute(Object inputData) throws LogicServiceException;

    boolean isCompleted();

    void destroy();
}

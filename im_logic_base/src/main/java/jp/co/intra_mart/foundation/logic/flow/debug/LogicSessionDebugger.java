package jp.co.intra_mart.foundation.logic.flow.debug;

import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;
import jp.co.intra_mart.foundation.logic.flow.event.AbstractLogicHandle;
import jp.co.intra_mart.foundation.logic.flow.event.ExclusiveGatewayEventData;
import jp.co.intra_mart.foundation.logic.flow.event.LoopEventData;
import org.slf4j.Logger;

import java.util.Date;

/**
 * 流程会话调试器
 */
public class LogicSessionDebugger extends AbstractLogicHandle {

    private final String flowId;
    private final Logger logger;
    private Date startDate;

    public LogicSessionDebugger(String flowId, Logger logger) {
        super(false);
        this.flowId = flowId;
        this.logger = logger;

        logger.debug("create session. (flowId={})", flowId);
    }

    @Override
    protected void beginFlow(Object data) {
        this.startDate = new Date();

        logger.debug("[{}] execute session. (flowId={}, inputData={})", getEvent().getEventType(), flowId, data);
    }

    @Override
    protected void beforeExecution(LogicFlowElementDefinition definition, Object data) {
        logger.debug("[{}] execute task. (executeId={}, taskId={}, inputData={})", getEvent().getEventType(), definition.getExecuteId(), definition.getKey(), data);
    }

    @Override
    protected void afterExecution(LogicFlowElementDefinition definition, Object data) {
        logger.debug("[{}] task result. (executeId={}, taskId={}, result={})", getEvent().getEventType(), definition.getExecuteId(), definition.getKey(), data);
    }

    @Override
    protected void endFlow(Object data) {
        if (startDate == null) {
            logger.debug("[{}] session result. (flowId={}, result={})", getEvent().getEventType(), flowId, data);
        } else {
            long executeTime = new Date().getTime() - startDate.getTime();
            logger.debug("[{}] session result. (flowId={}, executionTime={}ms, result={})", getEvent().getEventType(), flowId, executeTime, data);
        }
    }

    @Override
    protected void loopEvent(LogicFlowElementDefinition definition, LoopEventData data) {
        if (data.getItem() == null) {
            logger.debug("[{}] loop event. (executeId={}, index={})", getEvent().getEventType(), definition.getExecuteId(), data.getIndex());
        } else {
            logger.debug("[{}] loop event. (executeId={}, index={}, item={})", getEvent().getEventType(), definition.getExecuteId(), data.getIndex(), data.getItem());
        }
    }

    @Override
    protected void loopEndEvent(LogicFlowElementDefinition definition) {
        logger.debug("[{}] loop event end. (executeId={})", getEvent().getEventType(), definition.getExecuteId());
    }

    @Override
    protected void exclusiveGatewayEvent(LogicFlowElementDefinition definition, ExclusiveGatewayEventData data) {
        if (data.getCondition() != null) {
            logger.debug("[{}] condition [{}] is {}. (executeId={})", getEvent().getEventType(), data.getCondition(), data.isResult(), definition.getExecuteId());
        } else {
            logger.debug("[{}] next executeId is {}.", getEvent().getEventType(), data.getNextExecuteId());
        }
    }

    @Override
    protected void executionInformation(LogicFlowElementDefinition definition, String message) {
        logger.info(message);
    }

    @Override
    protected void executionWarning(LogicFlowElementDefinition definition, String message) {
        logger.warn(message);
    }
}

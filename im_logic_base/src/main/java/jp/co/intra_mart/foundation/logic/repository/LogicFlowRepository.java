package jp.co.intra_mart.foundation.logic.repository;

import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowCategory;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;

public interface LogicFlowRepository {

    LogicFlowCategory getCategory(String categoryId);

    void registerCategory(LogicFlowCategory category) throws LogicServiceException;

    void updateCategory(LogicFlowCategory category) throws LogicServiceException;

    void deleteCategory(LogicFlowCategory category) throws LogicServiceException;

    LogicFlowDefinition getLogicFlow(String flowId);

    LogicFlowDefinition getLogicFlow(String flowId, int version);

    void registerLogicFlow(LogicFlowDefinition definition) throws LogicServiceException;

    void updateLogicFlow(LogicFlowDefinition definition) throws LogicServiceException;

    void deleteLogicFlow(String flowId) throws LogicServiceException;

    void deleteLogicFlow(String flowId, int version) throws LogicServiceException;
}

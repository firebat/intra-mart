package jp.co.intra_mart.system.logic.repository;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.exception.LogicServiceException;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowCategory;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;
import jp.co.intra_mart.foundation.logic.repository.LogicFlowRepository;

import java.util.Map;

public class StandardLogicFlowRepository implements LogicFlowRepository {

    // TODO store in database
    private final Map<String, LogicFlowCategory> categories = Maps.newHashMap();
    private final Map<String, LogicFlowDefinition> definitions = Maps.newHashMap();

    @Override
    public LogicFlowCategory getCategory(String categoryId) {
        return categories.get(categoryId);
    }

    @Override
    public void registerCategory(LogicFlowCategory category) throws LogicServiceException {
        categories.put(category.getCategoryId(), category);
    }

    @Override
    public void updateCategory(LogicFlowCategory category) throws LogicServiceException {
        categories.put(category.getCategoryId(), category);
    }

    @Override
    public void deleteCategory(LogicFlowCategory category) throws LogicServiceException {
        categories.remove(category.getCategoryId());
    }

    @Override
    public LogicFlowDefinition getLogicFlow(String flowId) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(flowId), "flowId is not specified.");
        return definitions.get(flowId);
    }

    @Override
    public LogicFlowDefinition getLogicFlow(String flowId, int version) {
        return definitions.get(flowId);
    }

    @Override
    public void registerLogicFlow(LogicFlowDefinition definition) throws LogicServiceException {
        checkDefinition(definition);
        definitions.put(definition.getFlowId(), definition);
    }

    @Override
    public void updateLogicFlow(LogicFlowDefinition definition) throws LogicServiceException {
        definitions.put(definition.getFlowId(), definition);
    }

    @Override
    public void deleteLogicFlow(String flowId) throws LogicServiceException {
        definitions.remove(flowId);
    }

    @Override
    public void deleteLogicFlow(String flowId, int version) throws LogicServiceException {
        definitions.remove(flowId);
    }

    private void checkDefinition(LogicFlowDefinition definition) {
        Preconditions.checkNotNull(definition, "definition is not specified.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(definition.getFlowId()), "flowId is not specified.");
    }
}
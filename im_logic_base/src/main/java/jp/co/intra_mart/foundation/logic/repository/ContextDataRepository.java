package jp.co.intra_mart.foundation.logic.repository;

import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;

import java.util.Collection;
import java.util.Map;

public interface ContextDataRepository {

    Collection<String> getSessionFieldNames();

    String getDisplayName(String fieldName);

    DataDefinition getContextDataDefinition(String fieldName);

    Map<String, Object> createContextData(LogicFlow flow);

}

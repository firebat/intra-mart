package jp.co.intra_mart.foundation.logic.data.mapping;

import jp.co.intra_mart.foundation.logic.data.DataDefinition;

import java.util.Map;

public interface MappingContext {

    DataDefinition getTargetDataDefinition();

    DataDefinition findDataDefinition(String name);

    Map<String, Object> getSource();
}

package jp.co.intra_mart.system.logic.factory;

import jp.co.intra_mart.foundation.logic.LogicFlow;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;

public interface ContextDataFactory {

    String getSessionDataFieldName();

    String getDisplayName();

    DataDefinition getDataDefinition();

    Object createContextData(LogicFlow flow);
}

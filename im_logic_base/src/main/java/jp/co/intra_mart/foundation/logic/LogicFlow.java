package jp.co.intra_mart.foundation.logic;

import jp.co.intra_mart.foundation.logic.element.metadata.Metadata;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowDefinition;
import jp.co.intra_mart.foundation.logic.flow.LogicFlowElementDefinition;

import java.util.Collection;

/**
 * 逻辑流程
 */
public interface LogicFlow {

    String getFlowId();

    LogicFlowDefinition getDefinition();

    boolean isTransaction();

    LogicFlowElementDefinition getFlowElementDefinition(String executeId);

    Collection<LogicFlowElementDefinition> getNextElementDefinitions(String executeId);

    Collection<LogicFlowElementDefinition> getPrevElementDefinitions(String executeId);

    Metadata getElementMetadataByAlias(String alias);
}

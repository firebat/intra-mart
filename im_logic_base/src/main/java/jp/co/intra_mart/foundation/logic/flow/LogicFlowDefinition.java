package jp.co.intra_mart.foundation.logic.flow;

import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


@Getter
@Setter
public class LogicFlowDefinition implements Serializable {

    private String flowId;

    private int version;

    private String categoryId;

    private String flowName;

    private String notes;

    private boolean transaction;

    private LogicFlowConstants constants;

    private DataDefinition variablesDataDefinition;

    private Collection<LogicFlowElementDefinition> flowElements;

    private DataDefinition inputDataDefinition;

    private DataDefinition outputDataDefinition;

    private Map<String, Object> additional;
}

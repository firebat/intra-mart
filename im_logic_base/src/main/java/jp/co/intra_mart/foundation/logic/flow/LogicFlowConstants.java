package jp.co.intra_mart.foundation.logic.flow;

import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class LogicFlowConstants implements Serializable {

    private Map<String, Object> data;
    private Map<String, String> description;
    private DataDefinition dataDefinition;
}

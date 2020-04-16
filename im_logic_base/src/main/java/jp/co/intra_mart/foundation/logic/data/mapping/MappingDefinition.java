package jp.co.intra_mart.foundation.logic.data.mapping;

import java.io.Serializable;
import java.util.List;

public interface MappingDefinition extends Serializable {

    List<MappingRule> getMappingRules();
}

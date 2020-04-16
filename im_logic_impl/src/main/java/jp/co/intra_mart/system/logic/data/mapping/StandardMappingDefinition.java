package jp.co.intra_mart.system.logic.data.mapping;

import com.google.common.collect.Lists;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingDefinition;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingRule;
import lombok.Getter;

import java.util.List;

@Getter
public class StandardMappingDefinition implements MappingDefinition {

    private List<MappingRule> mappingRules = Lists.newArrayList();
}

package jp.co.intra_mart.system.logic.data.mapping;

import jp.co.intra_mart.foundation.logic.data.mapping.MappingRule;
import jp.co.intra_mart.foundation.logic.data.mapping.Node;
import jp.co.intra_mart.foundation.logic.data.mapping.Path;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StandardMappingRule implements MappingRule, Serializable {

    private String id;
    private Node source;
    private Path target;
}

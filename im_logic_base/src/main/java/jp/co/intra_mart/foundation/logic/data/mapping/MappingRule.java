package jp.co.intra_mart.foundation.logic.data.mapping;

import java.io.Serializable;

public interface MappingRule extends Serializable {

    String getId();

    Node getSource();

    Path getTarget();
}

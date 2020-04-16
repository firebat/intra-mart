package jp.co.intra_mart.foundation.logic.data.mapping;

import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.expression.EvaluateException;

import java.io.Serializable;
import java.util.List;

public interface Node extends Serializable {

    TypeDefinition<?> getTypeDefinition(MappingContext context) throws EvaluateException;

    List<Node> getParentNodes();
}

package jp.co.intra_mart.system.logic.data.mapping;

import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.mapping.MappingContext;
import jp.co.intra_mart.foundation.logic.data.mapping.Node;
import jp.co.intra_mart.foundation.logic.data.mapping.Path;
import jp.co.intra_mart.foundation.logic.data.mapping.Value;
import jp.co.intra_mart.foundation.logic.expression.EvaluateException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class StandardValue implements Value {

    @Getter
    private final Path path;

    @Override
    public TypeDefinition<?> getTypeDefinition(MappingContext context) throws EvaluateException {

        DataDefinition dataDefinition = context.findDataDefinition(this.path.getAlias());
        if (dataDefinition == null) {
            throw new EvaluateException("DataDefinition not found. (alias=" + path.getAlias() + ")");
        }

        // 数据级联访问
        TypeDefinition<?> type = dataDefinition.getTypeDefinitionByReference(dataDefinition.getEntrypoint());

        for (String name : path.getFragments()) {
            PropertyDefinition property = type.getPropertyByName(name);
            if (property == null) {
                throw new EvaluateException(name + " property not defined.");
            }
            type = dataDefinition.getTypeDefinitionByReference(property);
        }

        return type;
    }

    @Override
    public List<Node> getParentNodes() {
        return Collections.emptyList();
    }
}

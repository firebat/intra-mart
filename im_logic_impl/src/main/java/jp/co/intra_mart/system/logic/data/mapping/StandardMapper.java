package jp.co.intra_mart.system.logic.data.mapping;

import com.google.common.base.Preconditions;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.data.Entrypoint;
import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.mapping.*;
import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;
import jp.co.intra_mart.foundation.logic.exception.MappingException;
import jp.co.intra_mart.foundation.logic.expression.EvaluateException;

import java.util.Iterator;

public class StandardMapper implements Mapper {

    private final MappingDefinition mappingDefinition;

    public StandardMapper(MappingDefinition mappingDefinition) {
        this.mappingDefinition = mappingDefinition;
    }

    @Override
    public Object map(MappingContext context) throws MappingException {

        if (mappingDefinition == null || mappingDefinition.getMappingRules() == null || mappingDefinition.getMappingRules().isEmpty()) {
            return null;
        }

        // 根据映射规则，整合上下文中的数据，构建下级节点输入
        Object result = null;
        for (MappingRule rule : mappingDefinition.getMappingRules()) {
            Node source = rule.getSource();
            Preconditions.checkArgument(source instanceof Value, "unsupported function");
            result = process(context, result, (Value) source, rule.getTarget());
        }

        return result;
    }

    private Object process(MappingContext context, Object result, Value source, Path target) throws MappingException {
        try {
            Object value = readValue(context, source.getPath());
            return setValue(context, target, result, value);
        } catch (EvaluateException e) {
            throw new MappingException(e);
        }
    }


    private Object setValue(MappingContext context, Path path, Object root, Object value) throws MappingException {

        if (path.getFragments().size() == 0) {
            return value;
        }

        // 根据path信息，构建result结构
        DataDefinition targetDataDefinition = context.getTargetDataDefinition();
        Entrypoint targetEntryPoint = targetDataDefinition.getEntrypoint();
        TypeDefinition<?> targetTypeDefinition = targetDataDefinition.getTypeDefinitionByReference(targetEntryPoint);

        if (targetEntryPoint.isListable()) {
            // TODO listable
            throw new UnsupportedOperationException("listable is unsupported.");
        }

        Object parent = root == null ? targetTypeDefinition.newInstance() : root;
        Object result = parent;

        Iterator<String> iterator = path.getFragments().iterator();
        while (iterator.hasNext()) {
            String fragment = iterator.next();
            PropertyDefinition targetPropertyDefinition = targetTypeDefinition.getPropertyByName(fragment);
            if (targetPropertyDefinition == null) {
                throw new MappingException("property not found. (property name=" + fragment + ", TypeDefinition=" + targetTypeDefinition + ")");
            }

            if (!iterator.hasNext()) {
                targetPropertyDefinition.setValue(parent, value);
                break;
            }

            targetTypeDefinition = targetDataDefinition.getTypeDefinitionByReference(targetPropertyDefinition);
            Object property = targetPropertyDefinition.getValue(parent);
            if (property == null) {
                Object newProperty = targetTypeDefinition.newInstance();
                targetPropertyDefinition.setValue(parent, newProperty);
                parent = newProperty;
            } else {
                parent = property;
            }
        }

        return result;
    }

    private Object readValue(MappingContext context, Path path) throws EvaluateException {

        // 结构定义
        DataDefinition dataDefinition = context.findDataDefinition(path.getAlias());
        if (dataDefinition == null) {
            throw new EvaluateException("data definition not found. (alias=" + path.getAlias() + ")");
        }

        // 类型定义
        Entrypoint entryPoint = dataDefinition.getEntrypoint();
        TypeDefinition<?> entryPointTypeDefinition = dataDefinition.getTypeDefinitionByReference(entryPoint);
        if (entryPointTypeDefinition == null) {
            throw new EvaluateException(" type definition not found. typeId=" + entryPoint.getTypeId());
        }

        Object value = context.getSource().get(path.getAlias());
        if (path.getFragments().size() == 0) {
            return value;
        }

        TypeDefinition<?> valueTypeDefinition = entryPointTypeDefinition;
        for (String fragment : path.getFragments()) {
            PropertyDefinition propertyDefinition = valueTypeDefinition.getPropertyByName(fragment);
            if (propertyDefinition == null) {
                throw new EvaluateException("property definition not found. fragment=" + fragment + ", path=" + path);
            }

            TypeDefinition<?> propertyTypeDefinition = dataDefinition.getTypeDefinitionByReference(propertyDefinition);
            if (propertyTypeDefinition == null) {
                throw new EvaluateException("type definition not found. typeId=" + propertyDefinition.getTypeId());
            }

            try {
                value = propertyDefinition.getValue(value);
            } catch (IllegalDataAccessException e) {
                throw new EvaluateException(e);
            }
        }

        return value;
    }
}

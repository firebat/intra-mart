package jp.co.intra_mart.system.logic.data.beans;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jp.co.intra_mart.foundation.logic.data.DataDefinition;
import jp.co.intra_mart.foundation.logic.data.Entrypoint;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.TypeDefinitionReference;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinitions;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

public class JavaBeansDataDefinition implements DataDefinition {

    private Map<String, TypeDefinition<?>> typeDefinitions = Maps.newHashMap();

    @Getter
    private Entrypoint entrypoint;

    @Getter
    private Class<?> entrypointType;

    @Getter
    private Class<?> entrypointTypeHint;

    public JavaBeansDataDefinition(Entrypoint entryPoint, Set<TypeDefinition<?>> typeDefinitions, Class<?> entryPointType, Class<?> entryPointTypeHint) {
        this.entrypoint = entryPoint;
        this.entrypointType = entryPointType;
        this.entrypointTypeHint = entryPointTypeHint;

        for (TypeDefinition<?> typeDefinition : typeDefinitions) {
            this.typeDefinitions.put(typeDefinition.getId(), typeDefinition);
        }

        if (this.typeDefinitions.containsKey(BasicTypeDefinitions.MAP.getId())) {
            this.typeDefinitions.put(BasicTypeDefinitions.ANY.getId(), BasicTypeDefinitions.ANY);
        }
    }

    @Override
    public Set<TypeDefinition<?>> getTypeDefinitions() {
        return Sets.newHashSet(typeDefinitions.values());
    }

    @Override
    public TypeDefinition<?> getTypeDefinitionByReference(TypeDefinitionReference reference) {
        return typeDefinitions.get(reference.getTypeId());
    }
}

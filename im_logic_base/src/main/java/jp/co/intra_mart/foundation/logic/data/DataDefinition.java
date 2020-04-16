package jp.co.intra_mart.foundation.logic.data;

import java.io.Serializable;
import java.util.Set;

/**
 * 数据结构定义，用于描述输入、输出等结构
 */
public interface DataDefinition extends Serializable {

    Entrypoint getEntrypoint();

    Set<TypeDefinition<?>> getTypeDefinitions();

    TypeDefinition<?> getTypeDefinitionByReference(TypeDefinitionReference reference);
}

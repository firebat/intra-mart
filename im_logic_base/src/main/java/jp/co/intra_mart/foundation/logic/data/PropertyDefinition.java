package jp.co.intra_mart.foundation.logic.data;

import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;

/**
 * 数据结构的属性定义
 */
public interface PropertyDefinition extends TypeDefinitionReference {

    String getName();

    String getDescription();

    Object getValue(Object parent) throws IllegalDataAccessException;

    void setValue(Object parent, Object value) throws IllegalDataAccessException;
}

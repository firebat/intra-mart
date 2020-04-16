package jp.co.intra_mart.foundation.logic.data;

import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;

import java.util.List;


/**
 * 数据类型定义，可用于类型扩展
 */
public interface TypeDefinition<T> {

    String getId();

    List<PropertyDefinition> getProperties();

    PropertyDefinition getPropertyByName(String name);

    Class<T> getType();

    T newInstance() throws IllegalDataAccessException;
}

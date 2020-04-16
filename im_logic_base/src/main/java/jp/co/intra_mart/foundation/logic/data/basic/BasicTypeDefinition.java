package jp.co.intra_mart.foundation.logic.data.basic;

import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class BasicTypeDefinition<T> implements TypeDefinition<T>, Serializable {

    @Override
    public List<PropertyDefinition> getProperties() {
        return Collections.emptyList();
    }

    @Override
    public PropertyDefinition getPropertyByName(String name) {
        return null;
    }

    @Override
    public T newInstance() throws IllegalDataAccessException {
        throw new IllegalDataAccessException("Can't create basic instance.");
    }

    @Override
    public String toString() {
        return "BasicTypeDefinition()";
    }

    @Override
    public int hashCode() {
        return (getId() == null ? 0 : getId().hashCode()) + 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return getId().equals(((BasicTypeDefinition<?>) obj).getId());
    }
}

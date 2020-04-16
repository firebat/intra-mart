package jp.co.intra_mart.system.logic.data.beans;

import com.google.common.base.Preconditions;
import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class JavaBeansTypeDefinition<T> implements TypeDefinition<T> {

    @Getter
    protected String id;

    @Getter
    protected Class<T> type;

    @Getter
    protected List<PropertyDefinition> properties;

    @Override
    public PropertyDefinition getPropertyByName(String name) {
        Preconditions.checkArgument(name != null, "name parameter should not be null.");
        for (PropertyDefinition property : properties) {
            if (name.equals(property.getName())) {
                return property;
            }
        }
        return null;
    }


    @Override
    public T newInstance() throws IllegalDataAccessException {
        try {
            return getType().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalDataAccessException(e);
        }
    }
}

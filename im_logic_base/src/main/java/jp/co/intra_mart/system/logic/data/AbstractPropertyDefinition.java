package jp.co.intra_mart.system.logic.data;

import com.google.common.base.Preconditions;
import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.exception.IllegalDataAccessException;
import lombok.Getter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public abstract class AbstractPropertyDefinition {

    @Getter
    protected final String name;
    @Getter
    protected final String typeId;
    @Getter
    protected final ListingType listingType;
    @Getter
    protected final boolean basicType;
    @Getter
    protected final boolean required;
    @Getter
    protected final Method readMethod;
    @Getter
    protected final Method writeMethod;
    @Getter
    protected final String description;

    protected AbstractPropertyDefinition(PropertyDescriptor pd, String typeId, ListingType listingType, boolean basicType, boolean required, String description) {
        Preconditions.checkNotNull(listingType, "listingType");
        this.name = pd.getName();
        this.typeId = typeId;
        this.listingType = listingType;
        this.basicType = basicType;
        this.required = required;
        this.readMethod = pd.getReadMethod();
        this.writeMethod = pd.getWriteMethod();
        this.description = description;
    }

    public Object getValue(Object parent) throws IllegalDataAccessException {
        try {
            return readMethod.invoke(parent);
        } catch (ReflectiveOperationException e) {
            throw new IllegalDataAccessException(e.getLocalizedMessage(), e);
        }
    }

    public void setValue(Object parent, Object value) throws IllegalDataAccessException {
        try {
            writeMethod.invoke(parent, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalDataAccessException(e.getLocalizedMessage(), e);
        }
    }

    public boolean isListable() {
        return listingType != ListingType.NONE;
    }
}

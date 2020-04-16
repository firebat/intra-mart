package jp.co.intra_mart.foundation.logic.data.basic;

import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.system.logic.data.AbstractPropertyDefinition;
import lombok.Getter;

import java.beans.PropertyDescriptor;

public class BasicPropertyDefinition extends AbstractPropertyDefinition implements PropertyDefinition {

    @Getter
    protected final Class<?> type;

    public BasicPropertyDefinition(PropertyDescriptor pd, ListingType listingType, boolean required) {
        this(pd, BasicTypeDefinitions.resolveTypeId(pd.getPropertyType()), listingType, true, required, null);
    }

    public BasicPropertyDefinition(PropertyDescriptor pd, ListingType listingType, boolean required, String description) {
        this(pd, BasicTypeDefinitions.resolveTypeId(pd.getPropertyType()), listingType, true, required, description);
    }

    public BasicPropertyDefinition(PropertyDescriptor pd, Class<?> componentType, ListingType listingType, boolean required) {
        this(pd, BasicTypeDefinitions.resolveTypeId(componentType), listingType, true, required, null);
    }

    public BasicPropertyDefinition(PropertyDescriptor pd, Class<?> componentType, ListingType listingType, boolean required, String description) {
        this(pd, BasicTypeDefinitions.resolveTypeId(componentType), listingType, true, required, description);
    }

    public BasicPropertyDefinition(PropertyDescriptor pd, String typeId, ListingType listingType, boolean basicType, boolean required, String description) {
        super(pd, typeId, listingType, basicType, required, description);
        this.type = pd.getPropertyType();
    }
}

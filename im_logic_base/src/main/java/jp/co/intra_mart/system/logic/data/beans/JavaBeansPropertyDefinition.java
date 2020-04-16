package jp.co.intra_mart.system.logic.data.beans;

import jp.co.intra_mart.foundation.logic.data.ListingType;
import jp.co.intra_mart.foundation.logic.data.PropertyDefinition;
import jp.co.intra_mart.system.logic.data.AbstractPropertyDefinition;

import java.beans.PropertyDescriptor;

public class JavaBeansPropertyDefinition extends AbstractPropertyDefinition implements PropertyDefinition {

    public JavaBeansPropertyDefinition(PropertyDescriptor pd, String typeId, ListingType listingType, boolean basicType, boolean required, String description) {
        super(pd, typeId, listingType, basicType, required, description);
    }
}

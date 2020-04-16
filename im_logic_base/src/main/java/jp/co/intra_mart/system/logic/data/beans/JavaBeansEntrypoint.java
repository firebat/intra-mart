package jp.co.intra_mart.system.logic.data.beans;

import jp.co.intra_mart.foundation.logic.data.Entrypoint;
import jp.co.intra_mart.foundation.logic.data.ListingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaBeansEntrypoint implements Entrypoint {

    private String typeId;
    private boolean basicType;
    private boolean required;
    private ListingType listingType;

    @Override
    public boolean isListable() {
        return listingType != ListingType.NONE;
    }
}

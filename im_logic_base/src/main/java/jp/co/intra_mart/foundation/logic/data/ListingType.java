package jp.co.intra_mart.foundation.logic.data;

import java.util.Collection;

public enum ListingType {

    NONE,
    LIST,
    ARRAY;

    public static ListingType detect(Class<?> clazz) {

        if (clazz == null) {
            return NONE;
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            return LIST;
        }

        if (clazz.isArray()) {
            return ARRAY;
        }

        return NONE;
    }

    public static ListingType detect(Object value) {

        if (value == null) {
            return NONE;
        }

        if (value instanceof Collection) {
            return LIST;
        }

        if (value.getClass().isArray()) {
            return ARRAY;
        }

        return NONE;
    }
}

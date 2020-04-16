package jp.co.intra_mart.system.logic.data;

import java.util.Collection;

public final class TypeNameGenerator {

    public static String generateTypeNameForJavaType(Class<?> javaType) {
        return strip(javaType.getName());
    }

    public static String generateTypeNameForJavaType(Class<?> type, Class<?> componentType) {
        if (type.isArray()) {
            return strip(componentType.getName() + "$array");
        }
        if (Collection.class.isAssignableFrom(type)) {
            return strip(componentType.getName() + "$list");
        }
        return strip(type.getName() + "_" + componentType.getName());
    }

    private static String strip(String text) {
        return text.replace('.', '_');
    }
}

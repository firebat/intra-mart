package jp.co.intra_mart.foundation.logic.data.basic;

import com.google.common.collect.Maps;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class BasicTypeDefinitions {

    public static final TypeDefinition<Object> ANY = new AnyTypeDefinition();
    public static final TypeDefinition<BigDecimal> BIGDECIMAL = new BigDecimalTypeDefinition();
    public static final TypeDefinition<BigInteger> BIGINTEGER = new BigIntegerTypeDefinition();
    public static final TypeDefinition<Boolean> BOOLEAN = new BooleanTypeDefinition();
    public static final TypeDefinition<Byte> BYTE = new ByteTypeDefinition();
    public static final TypeDefinition<Character> CHARACTER = new CharacterTypeDefinition();
    public static final TypeDefinition<Double> DOUBLE = new DoubleTypeDefinition();
    public static final TypeDefinition<Float> FLOAT = new FloatTypeDefinition();
    public static final TypeDefinition<Integer> INTEGER = new IntegerTypeDefinition();
    public static final TypeDefinition<Long> LONG = new LongTypeDefinition();
    public static final TypeDefinition<Short> SHORT = new ShortTypeDefinition();
    public static final TypeDefinition<String> STRING = new StringTypeDefinition();
    public static final TypeDefinition<Locale> LOCALE = new LocaleTypeDefinition();
    public static final TypeDefinition<TimeZone> TIMEZONE = new TimeZoneTypeDefinition();
    public static final TypeDefinition<Date> DATE = new DateTypeDefinition();
    public static final TypeDefinition<Map> MAP = new MapTypeDefinition();

    // class -> definition
    private static final Map<Class<?>, TypeDefinition<?>> TYPE_MAP = Maps.newHashMap();
    // class -> id
    private static final Map<Class<?>, String> REFERENCE_MAP = Maps.newHashMap();
    // id -> definition
    private static final Map<String, TypeDefinition<?>> ID_MAP = Maps.newHashMap();

    static {
        TYPE_MAP.put(BIGDECIMAL.getType(), BIGDECIMAL);
        TYPE_MAP.put(BIGINTEGER.getType(), BIGINTEGER);
        TYPE_MAP.put(BOOLEAN.getType(), BOOLEAN);
        TYPE_MAP.put(BYTE.getType(), BYTE);
        TYPE_MAP.put(CHARACTER.getType(), CHARACTER);
        TYPE_MAP.put(SHORT.getType(), SHORT);
        TYPE_MAP.put(DOUBLE.getType(), DOUBLE);
        TYPE_MAP.put(FLOAT.getType(), FLOAT);
        TYPE_MAP.put(INTEGER.getType(), INTEGER);
        TYPE_MAP.put(LONG.getType(), LONG);
        TYPE_MAP.put(STRING.getType(), STRING);
        TYPE_MAP.put(LOCALE.getType(), LOCALE);
        TYPE_MAP.put(TIMEZONE.getType(), TIMEZONE);
        TYPE_MAP.put(DATE.getType(), DATE);
        TYPE_MAP.put(MAP.getType(), MAP);

        TYPE_MAP.put(boolean.class, BOOLEAN);
        TYPE_MAP.put(byte.class, BYTE);
        TYPE_MAP.put(char.class, CHARACTER);
        TYPE_MAP.put(double.class, DOUBLE);
        TYPE_MAP.put(float.class, FLOAT);
        TYPE_MAP.put(int.class, INTEGER);
        TYPE_MAP.put(long.class, LONG);
        TYPE_MAP.put(short.class, SHORT);

        TYPE_MAP.put(ANY.getType(), ANY);

        TYPE_MAP.forEach((clazz, type) -> {
            REFERENCE_MAP.put(clazz, type.getId());
            ID_MAP.put(type.getId(), type);
        });
    }

    public static <T> BasicTypeDefinition<T> resolveTypeDefinition(Class<T> type) {
        if (type == null) {
            return null;
        }

        TypeDefinition<?> definition = TYPE_MAP.get(type);
        if (definition != null) {
            return (BasicTypeDefinition) definition;
        }

        for (Map.Entry<Class<?>, TypeDefinition<?>> entry : TYPE_MAP.entrySet()) {
            Class<?> clazz = entry.getKey();
            if (Object.class.equals(clazz) || clazz.isPrimitive()) {
                continue;
            }
            if (clazz.isAssignableFrom(type)) {
                return (BasicTypeDefinition) entry.getValue();
            }
        }

        return null;
    }

    public static String resolveTypeId(Class<?> type) {
        String typeId = REFERENCE_MAP.get(type);
        if (typeId != null) {
            return typeId;
        }

        for (Map.Entry<Class<?>, String> entry : REFERENCE_MAP.entrySet()) {
            Class<?> clazz = entry.getKey();
            if (Object.class.equals(clazz)) {
                continue;
            }
            if (clazz.isAssignableFrom(type)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static String resolveTypeId(BasicTypeDefinition<?> definition) {
        return REFERENCE_MAP.get(definition.getType());
    }

    public static TypeDefinition<?> resolveTypeDefinition(String typeId) {
        return ID_MAP.get(typeId);
    }

    public static boolean isBasicType(TypeDefinition<?> definition) {
        if (definition == null) {
            return false;
        }
        return ID_MAP.containsKey(definition.getId());
    }
}

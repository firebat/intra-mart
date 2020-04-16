package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinitions;
import jp.co.intra_mart.foundation.logic.data.converter.Converter;
import jp.co.intra_mart.foundation.logic.data.converter.ConverterContext;
import jp.co.intra_mart.foundation.logic.exception.TypeConvertionException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinitions.*;

public abstract class AbstractBasicTypeConverter<F> implements Converter {

    @Override
    public <T> T convert(Object value, ConverterContext context) throws TypeConvertionException {
        if (value == null) {
            return null;
        }

        TypeDefinition<?> targetTypeDefinition = context.getTargetTypeDefinition();
        try {
            if (STRING.equals(targetTypeDefinition)) {
                return (T) toString((F) value, targetTypeDefinition);
            } else if (BOOLEAN.equals(targetTypeDefinition)) {
                return (T) toBoolean((F) value, targetTypeDefinition);
            } else if (BYTE.equals(targetTypeDefinition)) {
                return (T) toByte((F) value, targetTypeDefinition);
            } else if (CHARACTER.equals(targetTypeDefinition)) {
                return (T) toCharacter((F) value, targetTypeDefinition);
            } else if (SHORT.equals(targetTypeDefinition)) {
                return (T) toShort((F) value, targetTypeDefinition);
            } else if (INTEGER.equals(targetTypeDefinition)) {
                return (T) toInteger((F) value, targetTypeDefinition);
            } else if (LONG.equals(targetTypeDefinition)) {
                return (T) toLong((F) value, targetTypeDefinition);
            } else if (FLOAT.equals(targetTypeDefinition)) {
                return (T) toFloat((F) value, targetTypeDefinition);
            } else if (DOUBLE.equals(targetTypeDefinition)) {
                return (T) toDouble((F) value, targetTypeDefinition);
            } else if (BIGDECIMAL.equals(targetTypeDefinition)) {
                return (T) toBigDecimal((F) value, targetTypeDefinition);
            } else if (BIGINTEGER.equals(targetTypeDefinition)) {
                return (T) toBigInteger((F) value, targetTypeDefinition);
            } else if (DATE.equals(targetTypeDefinition)) {
                return (T) toDate((F) value, targetTypeDefinition);
            } else if (LOCALE.equals(targetTypeDefinition)) {
                return (T) toLocale((F) value, targetTypeDefinition);
            } else if (TIMEZONE.equals(targetTypeDefinition)) {
                return (T) toTimeZone((F) value, targetTypeDefinition);
            } else if (ANY.equals(targetTypeDefinition)) {
                return (T) toAnyType((F) value, targetTypeDefinition);
            } else if (MAP.equals(targetTypeDefinition)) {
                return (T) toMap((F) value, targetTypeDefinition);
            }
        } catch (RuntimeException e) {
            throw new TypeConvertionException(e);
        }
        return null;
    }

    @Override
    public boolean isSupportType(TypeDefinition<?> sourceTypeDefinition, TypeDefinition<?> targetTypeDefinition) {
        BasicTypeDefinition<F> supportBasicType = BasicTypeDefinitions.resolveTypeDefinition(getType());

        if (supportBasicType == null) {
            throw new IllegalStateException("unknown type. (" + getType() + ")");
        }

        return supportBasicType.equals(sourceTypeDefinition) && targetTypeDefinition instanceof BasicTypeDefinition;
    }

    protected abstract Class<F> getType();

    protected String toString(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Boolean toBoolean(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Byte toByte(F value, TypeDefinition definition) {
        return null;
    }

    protected Character toCharacter(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Short toShort(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Integer toInteger(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Long toLong(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Double toDouble(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Float toFloat(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected BigDecimal toBigDecimal(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected BigInteger toBigInteger(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Date toDate(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Locale toLocale(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected TimeZone toTimeZone(F value, TypeDefinition<?> definition) {
        return null;
    }

    protected Object toAnyType(F value, TypeDefinition<?> definition) {
        return value;
    }

    protected Map toMap(F value, TypeDefinition<?> definition) {
        return null;
    }
}

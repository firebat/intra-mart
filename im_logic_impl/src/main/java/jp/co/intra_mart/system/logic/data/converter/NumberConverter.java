package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public abstract class NumberConverter<N extends Number> extends AbstractBasicTypeConverter<N> {

    @Override
    protected Boolean toBoolean(N value, TypeDefinition<?> definition) {
        return value.doubleValue() != 0.0D;
    }

    @Override
    protected Byte toByte(N value, TypeDefinition definition) {
        return value.byteValue();
    }

    @Override
    protected Character toCharacter(N value, TypeDefinition<?> definition) {
        return (char)value.intValue();
    }

    @Override
    protected Short toShort(N value, TypeDefinition<?> definition) {
        return value.shortValue();
    }

    @Override
    protected Integer toInteger(N value, TypeDefinition<?> definition) {
        return value.intValue();
    }

    @Override
    protected Long toLong(N value, TypeDefinition<?> definition) {
        return value.longValue();
    }

    @Override
    protected Double toDouble(N value, TypeDefinition<?> definition) {
        return value.doubleValue();
    }

    @Override
    protected Float toFloat(N value, TypeDefinition<?> definition) {
        return value.floatValue();
    }

    @Override
    protected BigDecimal toBigDecimal(N value, TypeDefinition<?> definition) {
        return new BigDecimal(value.toString());
    }

    @Override
    protected BigInteger toBigInteger(N value, TypeDefinition<?> definition) {
        return new BigInteger(value.toString());
    }

    @Override
    protected Date toDate(N value, TypeDefinition<?> definition) {
        return new Date(value.longValue());
    }
}

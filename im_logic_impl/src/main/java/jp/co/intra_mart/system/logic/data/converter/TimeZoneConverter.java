package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.TimeZone;

@DataConverter
public class TimeZoneConverter extends AbstractBasicTypeConverter<TimeZone> {

    @Override
    protected Class<TimeZone> getType() {
        return TimeZone.class;
    }

    @Override
    protected String toString(TimeZone value, TypeDefinition<?> definition) {
        return value.getID();
    }

    @Override
    protected Byte toByte(TimeZone value, TypeDefinition definition) {
        return (byte) value.getRawOffset();
    }

    @Override
    protected Integer toInteger(TimeZone value, TypeDefinition<?> definition) {
        return value.getRawOffset();
    }

    @Override
    protected Long toLong(TimeZone value, TypeDefinition<?> definition) {
        return (long) value.getRawOffset();
    }

    @Override
    protected Double toDouble(TimeZone value, TypeDefinition<?> definition) {
        return (double) value.getRawOffset();
    }

    @Override
    protected Float toFloat(TimeZone value, TypeDefinition<?> definition) {
        return (float) value.getRawOffset();
    }

    @Override
    protected BigDecimal toBigDecimal(TimeZone value, TypeDefinition<?> definition) {
        return new BigDecimal(value.getRawOffset());
    }

    @Override
    protected BigInteger toBigInteger(TimeZone value, TypeDefinition<?> definition) {
        return new BigInteger(String.valueOf(value.getRawOffset()));
    }
}

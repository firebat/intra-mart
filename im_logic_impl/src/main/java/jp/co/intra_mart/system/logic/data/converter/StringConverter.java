package jp.co.intra_mart.system.logic.data.converter;

import com.google.common.base.Strings;
import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@DataConverter
public class StringConverter extends AbstractBasicTypeConverter<String> {

    @Override
    protected Class<String> getType() {
        return String.class;
    }

    @Override
    protected Boolean toBoolean(String value, TypeDefinition<?> definition) {
        return Boolean.valueOf(value);
    }

    @Override
    protected Byte toByte(String value, TypeDefinition definition) {
        return Byte.valueOf(value);
    }

    @Override
    protected Character toCharacter(String value, TypeDefinition<?> definition) {
        return Strings.isNullOrEmpty(value) ? null : value.charAt(0);
    }

    @Override
    protected Integer toInteger(String value, TypeDefinition<?> definition) {
        return Integer.valueOf(value);
    }

    @Override
    protected Long toLong(String value, TypeDefinition<?> definition) {
        return Long.valueOf(value);
    }

    @Override
    protected Double toDouble(String value, TypeDefinition<?> definition) {
        return Double.valueOf(value);
    }

    @Override
    protected BigDecimal toBigDecimal(String value, TypeDefinition<?> definition) {
        return new BigDecimal(value);
    }

    @Override
    protected BigInteger toBigInteger(String value, TypeDefinition<?> definition) {
        return new BigInteger(value);
    }

    @Override
    protected Date toDate(String value, TypeDefinition<?> definition) {
        // TODO date string format
        return super.toDate(value, definition);
    }

    @Override
    protected Locale toLocale(String value, TypeDefinition<?> definition) {
        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.toString().equals(value)) {
                return locale;
            }
        }
        return null;
    }

    @Override
    protected TimeZone toTimeZone(String value, TypeDefinition<?> definition) {
        return TimeZone.getTimeZone(value);
    }
}

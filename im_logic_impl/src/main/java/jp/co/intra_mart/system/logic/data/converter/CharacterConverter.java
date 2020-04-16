package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;

@DataConverter
public class CharacterConverter extends AbstractBasicTypeConverter<Character> {

    @Override
    protected Class<Character> getType() {
        return Character.class;
    }

    @Override
    protected Boolean toBoolean(Character value, TypeDefinition<?> definition) {
        return value != '0';
    }

    @Override
    protected Byte toByte(Character value, TypeDefinition definition) {
        return (byte) value.charValue();
    }

    @Override
    protected Character toCharacter(Character value, TypeDefinition<?> definition) {
        return value;
    }

    @Override
    protected Short toShort(Character value, TypeDefinition<?> definition) {
        return (short) value.charValue();
    }

    @Override
    protected Integer toInteger(Character value, TypeDefinition<?> definition) {
        return (int) value;
    }

    @Override
    protected Long toLong(Character value, TypeDefinition<?> definition) {
        return (long) value;
    }

    @Override
    protected Double toDouble(Character value, TypeDefinition<?> definition) {
        return (double) value;
    }

    @Override
    protected Float toFloat(Character value, TypeDefinition<?> definition) {
        return (float) value;
    }

    @Override
    protected BigDecimal toBigDecimal(Character value, TypeDefinition<?> definition) {
        return new BigDecimal((int) value);
    }

    @Override
    protected BigInteger toBigInteger(Character value, TypeDefinition<?> definition) {
        return new BigInteger(String.valueOf((int) value));
    }
}

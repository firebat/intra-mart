package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;

@DataConverter
public class BigDecimalConverter extends NumberConverter<BigDecimal> {

    @Override
    protected Class<BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override
    protected String toString(BigDecimal value, TypeDefinition<?> definition) {
        return value.toPlainString();
    }

    @Override
    protected Boolean toBoolean(BigDecimal value, TypeDefinition<?> definition) {
        return !BigDecimal.ZERO.equals(value);
    }

    @Override
    protected BigInteger toBigInteger(BigDecimal value, TypeDefinition<?> definition) {
        return value.toBigInteger();
    }
}

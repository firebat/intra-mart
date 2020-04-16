package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;

@DataConverter
public class BigIntegerConverter extends NumberConverter<BigInteger> {

    @Override
    protected Class<BigInteger> getType() {
        return BigInteger.class;
    }

    @Override
    protected BigDecimal toBigDecimal(BigInteger value, TypeDefinition<?> definition) {
        return new BigDecimal(value);
    }
}

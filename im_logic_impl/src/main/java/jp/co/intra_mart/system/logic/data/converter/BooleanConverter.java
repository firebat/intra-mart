package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.zip.ZipEntry;

@DataConverter
public class BooleanConverter extends AbstractBasicTypeConverter<Boolean> {

    @Override
    protected Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    protected Boolean toBoolean(Boolean value, TypeDefinition<?> definition) {
        return value;
    }

    @Override
    protected Byte toByte(Boolean value, TypeDefinition definition) {
        return value ? (byte) 1 : (byte) 0;
    }

    @Override
    protected Character toCharacter(Boolean value, TypeDefinition<?> definition) {
        return value ? '1' : '0';
    }

    @Override
    protected Integer toInteger(Boolean value, TypeDefinition<?> definition) {
        return value ? 1 : 0;
    }

    @Override
    protected Long toLong(Boolean value, TypeDefinition<?> definition) {
        return value ? 1L : 0L;
    }

    @Override
    protected Double toDouble(Boolean value, TypeDefinition<?> definition) {
        return value ? 1D : 0D;
    }

    @Override
    protected Float toFloat(Boolean value, TypeDefinition<?> definition) {
        return value ? 1F : 0F;
    }

    @Override
    protected BigDecimal toBigDecimal(Boolean value, TypeDefinition<?> definition) {
        return value ? BigDecimal.ONE : BigDecimal.ZERO;
    }

    @Override
    protected BigInteger toBigInteger(Boolean value, TypeDefinition<?> definition) {
        return value ? BigInteger.ONE : BigInteger.ZERO;
    }
}

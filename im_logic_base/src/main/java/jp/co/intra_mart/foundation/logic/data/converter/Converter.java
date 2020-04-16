package jp.co.intra_mart.foundation.logic.data.converter;

import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.exception.TypeConvertionException;

public interface Converter {

    <T> T convert(Object value, ConverterContext context) throws TypeConvertionException;

    boolean isSupportType(TypeDefinition<?> sourceTypeDefinition, TypeDefinition<?> targetTypeDefinition);
}

package jp.co.intra_mart.foundation.logic.repository;

import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.converter.Converter;

public interface ConverterRepository {

    Converter getConverter(TypeDefinition<?> fromType, TypeDefinition<?> toType);

    void registerConverter(Converter converter);
}

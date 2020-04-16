package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.LogicServiceProvider;
import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinition;
import jp.co.intra_mart.foundation.logic.data.basic.BasicTypeDefinitions;
import jp.co.intra_mart.foundation.logic.data.converter.Converter;
import jp.co.intra_mart.foundation.logic.data.converter.ConverterContext;
import jp.co.intra_mart.foundation.logic.exception.TypeConvertionException;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;

@DataConverter
public class AnyTypeConverter extends AbstractBasicTypeConverter<Object> {

    @Override
    protected Class<Object> getType() {
        return Object.class;
    }

    @Override
    public <T> T convert(Object value, ConverterContext context) throws TypeConvertionException {
        if (value == null) {
            return null;
        }

        BasicTypeDefinition<?> fromTypeDefinition = BasicTypeDefinitions.resolveTypeDefinition(value.getClass());
        if (fromTypeDefinition == null) {
            return null;
        }

        ConverterRepository converterRepository = LogicServiceProvider.getInstance().getConverterRepository();
        Converter converter = converterRepository.getConverter(fromTypeDefinition, context.getTargetTypeDefinition());
        if (converter == null || converter == this) {
            return null;
        }

        ConverterContext converterContext = context.createChildContext();
        converterContext.setSourceTypeDefinition(fromTypeDefinition);
        converterContext.setSourceListingType(context.getSourceListingType());
        converterContext.setTargetTypeDefinition(context.getTargetTypeDefinition());
        converterContext.setTargetListingType(context.getTargetListingType());

        return converter.convert(value, converterContext);
    }

    @Override
    public boolean isSupportType(TypeDefinition<?> sourceTypeDefinition, TypeDefinition<?> targetTypeDefinition) {
        return BasicTypeDefinitions.ANY.equals(sourceTypeDefinition) && targetTypeDefinition instanceof BasicTypeDefinition;
    }
}

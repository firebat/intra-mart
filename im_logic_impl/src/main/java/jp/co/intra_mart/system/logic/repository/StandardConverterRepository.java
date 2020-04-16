package jp.co.intra_mart.system.logic.repository;

import jp.co.intra_mart.foundation.logic.data.TypeDefinition;
import jp.co.intra_mart.foundation.logic.data.converter.Converter;
import jp.co.intra_mart.foundation.logic.repository.ConverterRepository;
import jp.co.intra_mart.system.logic.data.converter.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class StandardConverterRepository implements ConverterRepository {

    // TODO cache
    protected Set<Converter> converters = new LinkedHashSet<>();

    public StandardConverterRepository() {
        // TODO scan @DataConverter
        registerConverter(new AnyTypeConverter());
        registerConverter(new BigDecimalConverter());
        registerConverter(new BooleanConverter());
        registerConverter(new CharacterConverter());
        registerConverter(new DoubleConverter());
        registerConverter(new FloatConverter());
        registerConverter(new IntegerConverter());
        registerConverter(new LocaleConverter());
        registerConverter(new LongConverter());
        registerConverter(new MapConverter());
        registerConverter(new StringConverter());
        registerConverter(new TimeZoneConverter());

    }
    @Override
    public Converter getConverter(TypeDefinition<?> fromType, TypeDefinition<?> toType) {
        for (Converter converter : this.converters) {
            if (converter.isSupportType(fromType, toType)) {
                return converter;
            }
        }
        return null;
    }

    @Override
    public void registerConverter(Converter converter) {
        converters.add(converter);
    }
}

package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.util.Locale;

@DataConverter
public class LocaleConverter extends AbstractBasicTypeConverter<Locale> {

    @Override
    protected Locale toLocale(Locale value, TypeDefinition<?> definition) {
        return value;
    }

    @Override
    protected Class<Locale> getType() {
        return Locale.class;
    }
}

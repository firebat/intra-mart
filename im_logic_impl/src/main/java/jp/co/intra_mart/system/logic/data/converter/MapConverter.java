package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;
import jp.co.intra_mart.foundation.logic.data.TypeDefinition;

import java.util.Map;

@DataConverter
public class MapConverter extends AbstractBasicTypeConverter<Map<String, ?>> {

    @Override
    protected Map toMap(Map<String, ?> value, TypeDefinition<?> definition) {
        return value;
    }

    @Override
    protected Class<Map<String, ?>> getType() {
        return (Class) Map.class;
    }
}

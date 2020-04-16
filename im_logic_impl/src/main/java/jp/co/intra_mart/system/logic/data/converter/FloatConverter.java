package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;

@DataConverter
public class FloatConverter extends NumberConverter<Float> {

    @Override
    protected Class<Float> getType() {
        return Float.class;
    }
}

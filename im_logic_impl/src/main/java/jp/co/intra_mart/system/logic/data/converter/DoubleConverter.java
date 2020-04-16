package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;

@DataConverter
public class DoubleConverter extends NumberConverter<Double> {

    @Override
    protected Class<Double> getType() {
        return Double.class;
    }
}

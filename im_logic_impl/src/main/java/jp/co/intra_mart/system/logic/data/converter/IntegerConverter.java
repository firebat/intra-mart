package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;

@DataConverter
public class IntegerConverter extends NumberConverter<Integer> {

    @Override
    protected Class<Integer> getType() {
        return Integer.class;
    }
}

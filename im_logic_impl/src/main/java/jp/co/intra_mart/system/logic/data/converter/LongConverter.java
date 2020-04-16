package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;

@DataConverter
public class LongConverter extends NumberConverter<Long> {

    @Override
    protected Class<Long> getType() {
        return Long.class;
    }
}

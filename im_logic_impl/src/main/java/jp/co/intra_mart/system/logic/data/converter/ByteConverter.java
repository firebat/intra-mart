package jp.co.intra_mart.system.logic.data.converter;

import jp.co.intra_mart.foundation.logic.annotation.DataConverter;

@DataConverter
public class ByteConverter extends NumberConverter<Byte> {

    @Override
    protected Class<Byte> getType() {
        return Byte.class;
    }
}

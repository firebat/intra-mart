package jp.co.intra_mart.foundation.logic.data.basic;

public class LongTypeDefinition extends BasicTypeDefinition<Long> {

    @Override
    public String getId() {
        return "long";
    }

    @Override
    public Class<Long> getType() {
        return Long.class;
    }
}

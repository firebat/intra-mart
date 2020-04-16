package jp.co.intra_mart.foundation.logic.data.basic;

public class ShortTypeDefinition extends BasicTypeDefinition<Short> {
    @Override
    public String getId() {
        return "short";
    }

    @Override
    public Class<Short> getType() {
        return Short.class;
    }
}

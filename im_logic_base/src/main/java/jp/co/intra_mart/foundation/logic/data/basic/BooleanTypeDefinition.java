package jp.co.intra_mart.foundation.logic.data.basic;

public class BooleanTypeDefinition extends BasicTypeDefinition<Boolean> {

    @Override
    public String getId() {
        return "boolean";
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }
}

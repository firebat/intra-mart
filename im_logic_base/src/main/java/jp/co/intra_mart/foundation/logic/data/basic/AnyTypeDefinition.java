package jp.co.intra_mart.foundation.logic.data.basic;

public class AnyTypeDefinition extends BasicTypeDefinition<Object> {

    @Override
    public String getId() {
        return "any";
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }
}

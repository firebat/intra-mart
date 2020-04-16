package jp.co.intra_mart.foundation.logic.data.basic;

public class IntegerTypeDefinition extends BasicTypeDefinition<Integer> {

    @Override
    public String getId() {
        return "integer";
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}

package jp.co.intra_mart.foundation.logic.data.basic;

public class StringTypeDefinition extends BasicTypeDefinition<String> {

    @Override
    public String getId() {
        return "string";
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}

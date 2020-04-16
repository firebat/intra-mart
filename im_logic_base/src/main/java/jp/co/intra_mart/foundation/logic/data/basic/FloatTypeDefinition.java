package jp.co.intra_mart.foundation.logic.data.basic;

public class FloatTypeDefinition extends BasicTypeDefinition<Float> {

    @Override
    public String getId() {
        return "float";
    }

    @Override
    public Class<Float> getType() {
        return Float.class;
    }
}

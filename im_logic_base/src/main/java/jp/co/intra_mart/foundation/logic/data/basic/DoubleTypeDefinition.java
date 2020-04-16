package jp.co.intra_mart.foundation.logic.data.basic;

public class DoubleTypeDefinition extends BasicTypeDefinition<Double> {

    @Override
    public String getId() {
        return "double";
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }
}

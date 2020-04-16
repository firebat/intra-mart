package jp.co.intra_mart.foundation.logic.data.basic;

import java.math.BigDecimal;

public class BigDecimalTypeDefinition extends BasicTypeDefinition<BigDecimal> {

    @Override
    public String getId() {
        return "bigdecimal";
    }

    @Override
    public Class<BigDecimal> getType() {
        return BigDecimal.class;
    }
}

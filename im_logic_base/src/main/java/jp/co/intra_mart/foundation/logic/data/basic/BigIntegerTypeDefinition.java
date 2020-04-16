package jp.co.intra_mart.foundation.logic.data.basic;

import java.math.BigInteger;

public class BigIntegerTypeDefinition extends BasicTypeDefinition<BigInteger> {

    @Override
    public String getId() {
        return "biginteger";
    }

    @Override
    public Class<BigInteger> getType() {
        return BigInteger.class;
    }
}

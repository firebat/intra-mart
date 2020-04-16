package jp.co.intra_mart.foundation.logic.data.basic;

public class ByteTypeDefinition extends BasicTypeDefinition<Byte> {

    @Override
    public String getId() {
        return "byte";
    }

    @Override
    public Class<Byte> getType() {
        return Byte.class;
    }
}

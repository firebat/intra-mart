package jp.co.intra_mart.foundation.logic.data.basic;

public class CharacterTypeDefinition extends BasicTypeDefinition<Character> {

    @Override
    public String getId() {
        return "character";
    }

    @Override
    public Class<Character> getType() {
        return Character.class;
    }
}

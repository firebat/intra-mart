package jp.co.intra_mart.foundation.logic.data.basic;

import java.util.Locale;

public class LocaleTypeDefinition extends BasicTypeDefinition<Locale> {
    @Override
    public String getId() {
        return "locale";
    }

    @Override
    public Class<Locale> getType() {
        return Locale.class;
    }
}

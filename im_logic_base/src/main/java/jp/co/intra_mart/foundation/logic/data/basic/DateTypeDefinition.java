package jp.co.intra_mart.foundation.logic.data.basic;

import java.util.Date;

public class DateTypeDefinition extends BasicTypeDefinition<Date> {

    @Override
    public String getId() {
        return "date";
    }

    @Override
    public Class<Date> getType() {
        return Date.class;
    }
}

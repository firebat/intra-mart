package jp.co.intra_mart.foundation.logic.data.basic;

import java.util.TimeZone;

public class TimeZoneTypeDefinition extends BasicTypeDefinition<TimeZone> {

    @Override
    public String getId() {
        return "timezone";
    }

    @Override
    public Class<TimeZone> getType() {
        return TimeZone.class;
    }
}

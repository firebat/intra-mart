package jp.co.intra_mart.foundation.logic.flow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExclusiveGatewayEventData {
    private final String condition;
    private final boolean result;
    private final String nextExecuteId;
}

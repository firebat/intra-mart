package jp.co.intra_mart.foundation.logic.flow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoopEventData {

    private final long index;
    private final Object item;
}

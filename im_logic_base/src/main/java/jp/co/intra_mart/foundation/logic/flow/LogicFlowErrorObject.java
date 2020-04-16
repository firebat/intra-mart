package jp.co.intra_mart.foundation.logic.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogicFlowErrorObject {

    private final String executeId;
    private final String errorMessage;
}

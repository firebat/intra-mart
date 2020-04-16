package jp.co.intra_mart.system.logic.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExecuteTaskResult {

    private String executeId;

    private boolean error;

    private String errorMessage;
}

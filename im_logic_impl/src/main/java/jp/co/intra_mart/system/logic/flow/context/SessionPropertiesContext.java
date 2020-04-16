package jp.co.intra_mart.system.logic.flow.context;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SessionPropertiesContext {

    private String flowId;
    private int version;
    private Date startTime;
}

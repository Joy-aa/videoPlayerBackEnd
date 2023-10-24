package org.newhome.req;

import lombok.Data;

@Data
public class UpdateHistoryReq {
    private String username;

    private String historyName;

    private String newName;
}

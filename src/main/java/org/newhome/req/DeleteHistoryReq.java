package org.newhome.req;

import lombok.Data;

@Data
public class DeleteHistoryReq {
    private String username;
    private String historyName;
}

package org.newhome.req;

import lombok.Data;

@Data
public class AddHistoryReq {
    private int userId;

    private int videoId;

    private Data watchTime;
}

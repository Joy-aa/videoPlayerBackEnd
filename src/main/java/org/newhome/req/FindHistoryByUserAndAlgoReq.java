package org.newhome.req;

import lombok.Data;

@Data
public class FindHistoryByUserAndAlgoReq {
    private String username;
    private String algorithm;
}

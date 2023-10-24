package org.newhome.req;

import lombok.Data;

@Data
public class AddHistoryReq {
    private String username;

    private String historyName;

    private String algorithm;

//    private String dataset;
    private int datasetId;

    private String modelPath;
}

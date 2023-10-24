package org.newhome.req;

import lombok.Data;

@Data
public class FindHistoryByUserAndDatasetReq {

    private String username;

    private String dataset;
    private int datasetId;
}

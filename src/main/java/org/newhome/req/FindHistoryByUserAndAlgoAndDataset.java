package org.newhome.req;

import lombok.Data;

@Data
public class FindHistoryByUserAndAlgoAndDataset {
    private String username;
    private String algorithm;
    private String dataset;
    private int datasetId;
}

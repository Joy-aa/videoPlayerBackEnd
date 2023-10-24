package org.newhome.req;

import lombok.Data;

@Data
public class FindModelByUserAndAlgoAndDataset {
    private String username;
    private String algorithm;
    private String dataset;
}

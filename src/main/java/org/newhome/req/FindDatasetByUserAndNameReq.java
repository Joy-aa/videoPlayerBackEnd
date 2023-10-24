package org.newhome.req;

import lombok.Data;

@Data
public class FindDatasetByUserAndNameReq {
    private String username;
    private String datasetName;
}

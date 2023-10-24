package org.newhome.req;

import lombok.Data;

@Data
public class AddModelReq {
    private String username;

    private String modelName;

    private String algorithm;

    private String dataset;
}

package org.newhome.req;

import lombok.Data;

@Data
public class UpdateModelReq {
    private String username;

    private String modelName;

    private String newName;
}

package org.newhome.req;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateUserHeadShotReq {
    @NonNull
    private String email;

    @NonNull
    private String username;

    @NonNull
    private String headshotname;

    @NonNull
    private String introduction;
}

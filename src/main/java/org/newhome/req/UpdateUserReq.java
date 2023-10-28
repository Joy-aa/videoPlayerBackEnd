package org.newhome.req;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateUserReq {
    @NonNull
    private String email;

    @NonNull
    private String username;

    @NonNull
    private String headshot;

    @NonNull
    private String introduction;
}

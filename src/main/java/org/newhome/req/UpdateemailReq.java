package org.newhome.req;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateemailReq {
    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String newEmail;
}

package org.newhome.req;

import lombok.Data;

@Data
public class RegisterReq {
    private String username;

    private String password;

    private String email;

    private String code;
}

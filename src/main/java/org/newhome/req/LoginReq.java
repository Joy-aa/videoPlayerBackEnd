package org.newhome.req;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
    private String code;
}

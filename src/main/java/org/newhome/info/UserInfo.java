package org.newhome.info;

import lombok.Data;

@Data
public class UserInfo {
    private Integer userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String headshot;

    private String introduction;
}

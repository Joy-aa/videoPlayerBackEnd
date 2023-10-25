package org.newhome.info;

import lombok.Data;
import org.newhome.entity.User;
import org.newhome.util.MyBeanUtils;

@Data
public class UserInfo {
    private Integer userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String headshot;

    private String introduction;

    public User change(){
        User user = new User();
        MyBeanUtils.copyProperties(this, user);
        return user;
    }
}

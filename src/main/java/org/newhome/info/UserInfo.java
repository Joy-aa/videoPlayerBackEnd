package org.newhome.info;

import lombok.Data;
import org.newhome.entity.User;
import org.newhome.util.MyBeanUtils;

@Data
public class UserInfo {

    private String username;

    private String password;

    private String salt;

    private String telephone;

    private String headshot;

    private Integer authority;

    public UserInfo(){}

    public UserInfo(User user){
        MyBeanUtils.copyProperties(user, this);
    }

    public User change(){
        User user = new User();
        MyBeanUtils.copyProperties(this, user);
        return user;
    }
}

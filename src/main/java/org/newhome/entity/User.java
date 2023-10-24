package org.newhome.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.newhome.info.UserInfo;
import org.newhome.util.MyBeanUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String username;

    private String password;

    private String salt;

    private String telephone;

    private String headshot;

    private Integer authority;

    public User(){}

    public User(UserInfo userInfo){
        MyBeanUtils.copyProperties(userInfo, this);
    }

    public UserInfo change(){
        UserInfo userInfo = new UserInfo();
        MyBeanUtils.copyProperties(this, userInfo);
        return userInfo;
    }


}

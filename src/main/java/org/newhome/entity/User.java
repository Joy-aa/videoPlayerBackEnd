package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.newhome.info.UserInfo;
import org.newhome.util.MyBeanUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String headshot;

    private String introduction;

    public UserInfo change(){
        UserInfo userInfo = new UserInfo();
        MyBeanUtils.copyProperties(this, userInfo);
        return userInfo;
    }

}

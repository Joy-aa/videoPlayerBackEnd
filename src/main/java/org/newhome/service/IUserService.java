package org.newhome.service;

import org.newhome.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.info.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
public interface IUserService extends IService<User> {
    public UserInfo findByEmail(String email);

    public UserInfo addUser(UserInfo userInfo);
}

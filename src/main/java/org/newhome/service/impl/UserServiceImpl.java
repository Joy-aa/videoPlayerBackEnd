package org.newhome.service.impl;

import org.newhome.entity.User;
import org.newhome.info.UserInfo;
import org.newhome.mapper.UserMapper;
import org.newhome.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserInfo findByEmail(String email) {
        User user = userMapper.findByEmail(email);
        if (user!=null){
            return user.change();
        }else{
            return null;
        }
    }

    public UserInfo addUser(UserInfo userInfo) {
        int num = userMapper.insert(userInfo.change());
        if(num > 0)
            return userInfo;
        else
            return null;
    }
}

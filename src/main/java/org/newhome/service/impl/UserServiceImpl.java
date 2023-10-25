package org.newhome.service.impl;

import org.newhome.entity.User;
import org.newhome.mapper.UserMapper;
import org.newhome.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}

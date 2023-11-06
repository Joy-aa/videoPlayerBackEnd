package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.User;
import org.newhome.service.UserService;
import org.newhome.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author Yuxin Wang
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-27 15:53:13
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public User addUser(User user) {
        int num = userMapper.insert(user);
        if(num > 0)
            return user;
        else
            return null;
    }

    @Override
    public void updateUserPassword(String email, String newPwd) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();

        lambdaUpdateWrapper.eq(User::getEmail, email)
                .set(User::getPassword, newPwd);
        userMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void updateUserEmail(String email, String newEmail) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();

        lambdaUpdateWrapper.eq(User::getEmail, email)
                .set(User::getEmail, newEmail);
        userMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public int updateUser(User user) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getEmail, user.getEmail())
                .set(User::getUsername, user.getUsername())
                .set(User::getHeadshot, user.getHeadshot())
                .set(User::getIntroduction, user.getIntroduction());

        userMapper.update(null, lambdaUpdateWrapper);
        return 1;
    }
    @Override
    public int updateUserImg(User user) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getEmail, user.getEmail())
                .set(User::getUsername, user.getUsername())
                .set(User::getHeadshotname, user.getHeadshotname())
                .set(User::getIntroduction, user.getIntroduction());

        userMapper.update(null, lambdaUpdateWrapper);
        return 1;
    }

    @Override
    public User findById(Integer userid1) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUserId, userid1);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getEmail, email);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public List<User> findUsers(String content) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.like(!ObjectUtils.isEmpty(content),User::getUsername,content)
                .or()
                .like(!ObjectUtils.isEmpty(content),User::getIntroduction,content);
        List<User> userList = userMapper.selectList(wrapper);
        return userList;
    }
}





package org.newhome.service;

import org.newhome.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-27 15:53:13
*/
public interface UserService extends IService<User> {
    public User addUser(User userInfo);

    public void updateUserEmail(String email, String newEmail);

    public void updateUserPassword(String email, String newPwd);

    public int updateUser(User user);

    public User findByEmail(String email);

    public List<User> findUsers(String content);

}

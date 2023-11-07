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
    User addUser(User userInfo);

    void updateUserEmail(String email, String newEmail);

    void updateUserPassword(String email, String newPwd);

    int updateUser(User user);

    int updateUserImg(User user);

    User findById(Integer userid1);

    User findByEmail(String email);

    List<User> findUsers(String content);

    List<User> getAllUsers();
}

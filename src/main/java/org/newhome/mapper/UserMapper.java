package org.newhome.mapper;

import org.apache.ibatis.annotations.Param;
import org.newhome.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
public interface UserMapper extends BaseMapper<User> {
    public User findByUsername(@Param("username") String username);

    public List<User> findAllUser();

    public int addUser(User user);

    public boolean updateUserPassword(String username,String password);

    public boolean updateUserTel(String username, String telephone);

    public boolean updateUserIdentify(String username, int identify);
}

package org.newhome.mapper;

import org.apache.ibatis.annotations.Param;
import org.newhome.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
public interface UserMapper extends BaseMapper<User> {
    public User findByEmail(@Param("email") String email);

}

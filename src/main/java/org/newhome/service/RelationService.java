package org.newhome.service;

import io.swagger.models.auth.In;
import org.newhome.entity.Relation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.entity.User;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【relation】的数据库操作Service
* @createDate 2023-10-27 15:58:23
*/
public interface RelationService extends IService<Relation> {

    Relation addRelation(User user1, User user2, Integer kind);

    int deleteRelation(User user1, User user2, Integer kind);

    int modifyRelation(User user1, User user2, Integer kind);

    List<Relation> findFollows(Integer userid1, Integer kind);

    List<Relation> findFans(Integer userid2, Integer kind);

    Relation findRelation(Integer userid1, Integer userid2);

}

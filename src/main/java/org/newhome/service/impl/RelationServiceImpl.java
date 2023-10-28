package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Relation;
import org.newhome.entity.User;
import org.newhome.mapper.UserMapper;
import org.newhome.service.RelationService;
import org.newhome.mapper.RelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【relation】的数据库操作Service实现
* @createDate 2023-10-27 15:58:23
*/
@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation>
    implements RelationService{
    @Autowired
    RelationMapper relationMapper;

    @Override
    public Relation addRelation(User user1, User user2, Integer kind) {
        Relation relation = new Relation();
        relation.setUser1Id(user1.getUserId());
        relation.setUser2Id(user2.getUserId());
        relation.setKind(kind);
        int num = relationMapper.insert(relation);
        if(num > 0)
            return relation;
        else
            return null;
    }

    @Override
    public int deleteRelation(User user1, User user2, Integer kind) {
        Relation relation = new Relation();
        relation.setUser1Id(user1.getUserId());
        relation.setUser2Id(user2.getUserId());
        relation.setKind(kind);
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<Relation>();
        wrapper.eq(Relation::getUser1Id,user1.getUserId())
                .eq(Relation::getUser2Id,user2.getUserId())
                .eq(Relation::getKind,kind);
        int num = relationMapper.delete(wrapper);
        return num;
    }

    @Override
    public int modifyRelation(User user1, User user2, Integer kind) {
        Relation relation = new Relation();
        relation.setUser1Id(user1.getUserId());
        relation.setUser2Id(user2.getUserId());
        relation.setKind(kind);
        LambdaUpdateWrapper<Relation> wrapper = new LambdaUpdateWrapper<Relation>();
        wrapper.eq(Relation::getUser1Id,user1.getUserId())
                .eq(Relation::getUser2Id,user2.getUserId())
                .set(Relation::getKind,kind);
        int num = relationMapper.update(null, wrapper);
        return num;
    }

    @Override
    public List<Relation> findRelations(Integer userid1, Integer userid2, Integer kind) {
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<Relation>();
        wrapper.eq(!ObjectUtils.isEmpty(userid1),Relation::getUser1Id,userid1)
                .eq(!ObjectUtils.isEmpty(userid2),Relation::getUser2Id,userid2)
                .eq(!ObjectUtils.isEmpty(kind),Relation::getKind,kind);
        List<Relation> relationList = relationMapper.selectList(wrapper);
        return relationList;
    }
}





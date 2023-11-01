package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Star;
import org.newhome.service.StarService;
import org.newhome.mapper.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【star】的数据库操作Service实现
* @createDate 2023-10-27 15:58:16
*/
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star>
    implements StarService{

    @Autowired
    StarMapper starMapper;

    @Override
    public Star addStar(Star star) {
        int num = starMapper.insert(star);
        if(num > 0) {
            return star;
        }
        else {
            return null;
        }
    }

    @Override
    public int delete(int starId) {
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<Star>();
        wrapper.eq(Star::getStarId, starId);
        return starMapper.delete(wrapper);
    }

    @Override
    public int deleteAll(int userId) {
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getUserId, userId);
        return starMapper.delete(wrapper);
    }

    @Override
    public List<Star> getAll(int userId) {
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getUserId, userId);
        return starMapper.selectList(wrapper);
    }

    @Override
    public Star getOne(int userId, int videoId) {
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getUserId, userId)
                .eq(Star::getVideoid, videoId);
        return starMapper.selectOne(wrapper);
    }
}





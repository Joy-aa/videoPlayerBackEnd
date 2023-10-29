package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Relation;
import org.newhome.entity.Tag;
import org.newhome.entity.Tagrecord;
import org.newhome.entity.Video;
import org.newhome.service.TagrecordService;
import org.newhome.mapper.TagrecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【tagrecord】的数据库操作Service实现
* @createDate 2023-10-27 15:57:35
*/
@Service
public class TagrecordServiceImpl extends ServiceImpl<TagrecordMapper, Tagrecord>
    implements TagrecordService{

    @Autowired
    TagrecordMapper tagrecordMapper;
    @Override
    public Tagrecord addTagrecord(Video video , Tag tag){
        Tagrecord tagrecord = new Tagrecord();
        tagrecord.setVideoId(video.getVideoId());
        tagrecord.setTagId(tag.getTagId());
        int num = tagrecordMapper.insert(tagrecord);
        return tagrecord;
    }


    @Override
    public int deleteTagrecord(Video video , Tag tag){
        Tagrecord tagrecord = new Tagrecord();
        tagrecord.setVideoId(video.getVideoId());
        tagrecord.setTagId(tag.getTagId());
        LambdaQueryWrapper<Tagrecord> wrapper = new LambdaQueryWrapper<Tagrecord>();
        wrapper.eq(Tagrecord::getVideoId,video.getVideoId())
                .eq(Tagrecord::getTagId,tag.getTagId());
        int num = tagrecordMapper.delete(wrapper);
        return num;

    }

//    @Override
//    public int modifyTagrecord(Video video , Tag tag){
//        Tagrecord tagrecord = new Tagrecord();
//        tagrecord.setVideoId(video.getVideoId());
//        tagrecord.setTagId(tag.getTagId());
//        LambdaUpdateWrapper<Tagrecord> wrapper = new LambdaUpdateWrapper<Tagrecord>();
//        wrapper.eq(Tagrecord::getVideoId,video.getVideoId())
//                .set(Tagrecord::getTagId,tag.getTagId());
//        int num = tagrecordMapper.update(null,wrapper);
//        return num;
//
//    }

    //根据视频查找其tag
    @Override
    public List<Tagrecord> findTagrecord(Video video){
        LambdaQueryWrapper<Tagrecord> wrapper = new LambdaQueryWrapper<Tagrecord>();
        wrapper.eq(Tagrecord::getVideoId,video.getVideoId());
        List<Tagrecord> tagrecordList = tagrecordMapper.selectList(wrapper);
        return tagrecordList;
    }

    @Override
    public List<Tagrecord> findVideoByTag(Tag tag){
        LambdaQueryWrapper<Tagrecord> wrapper = new LambdaQueryWrapper<Tagrecord>();
        wrapper.eq(Tagrecord::getTagId, tag.getTagId());
        List<Tagrecord> tagrecordList = tagrecordMapper.selectList(wrapper);
        return tagrecordList;
    }

    @Override
    public Boolean isTaginVideo(Video video , Tag tag){
        LambdaQueryWrapper<Tagrecord> wrapper = new LambdaQueryWrapper<Tagrecord>();
        wrapper.eq(Tagrecord::getVideoId,video.getVideoId())
                .eq(Tagrecord::getTagId,tag.getTagId());
        Tagrecord tagrecord = tagrecordMapper.selectOne(wrapper);
        return tagrecord != null;
    }

}





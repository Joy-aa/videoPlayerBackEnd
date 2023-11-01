package org.newhome.service;

import org.newhome.entity.Tag;
import org.newhome.entity.Tagrecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.entity.Video;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【tagrecord】的数据库操作Service
* @createDate 2023-10-27 15:57:35
*/
public interface TagrecordService extends IService<Tagrecord> {

    Tagrecord addTagrecord(Video video , Tag tag);
    int deleteTagrecord(Video video , Tag tag);

//    int modifyTagrecord(Video video , Tag tag);

    //根据视频查找其tag
    List<Tagrecord> findTagrecord(Video video);
    //根据tag查找视频
    List<Tagrecord> findVideoByTag(Tag tag);

    Boolean isTaginVideo(Video video , Tag tag);



}

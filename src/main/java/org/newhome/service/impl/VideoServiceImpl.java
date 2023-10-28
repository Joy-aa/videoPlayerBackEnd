package org.newhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Video;
import org.newhome.service.VideoService;
import org.newhome.mapper.VideoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【video】的数据库操作Service实现
* @createDate 2023-10-27 15:56:47
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

    @Override
    public void uploadVideo(Video video) {

    }

    @Override
    public void deleteVideo(Integer videoid) {

    }

    @Override
    public void deleteVideos(List<Integer> videoIdList) {

    }

    @Override
    public void findVideos(Video video) {

    }
}





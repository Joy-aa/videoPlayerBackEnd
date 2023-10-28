package org.newhome.service;

import org.newhome.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【video】的数据库操作Service
* @createDate 2023-10-27 15:56:47
*/
public interface VideoService extends IService<Video> {

    void uploadVideo(Video video);

    void deleteVideo(Integer videoid);

    void deleteVideos(List<Integer> videoIdList);

    void findVideos(Video video);

    Video findById(int videoId);
}

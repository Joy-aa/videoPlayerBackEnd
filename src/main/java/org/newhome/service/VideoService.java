package org.newhome.service;

import org.newhome.entity.User;
import org.newhome.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【video】的数据库操作Service
* @createDate 2023-10-27 15:56:47
*/
public interface VideoService extends IService<Video> {

    int uploadVideo(Video video);

    int deleteVideo(Video video);

    int deleteVideos(List<Integer> videoList);

    void findVideos(Video video);

<<<<<<< HEAD
    Video findVideobyId(Integer videoid);

    //根据用户id查找视频
    List<Video> findVideoByUser(User user);

    //根据标题和简介模糊查找视频
    List<Video> findVideoByName(String content);

    void  updateVideoName(Video video,String newName);

    void updateVideoIntroduction(Video video,String newIntroduction);

=======
    Video findById(int videoId);
>>>>>>> 7929055deb260703bfa633e4ab34859897dd44a5
}

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

    List<Video> findVideos();


    Video findVideobyId(Integer videoid);

    //根据用户id查找视频
    List<Video> findVideoByUser(User user);

    //根据标题和简介模糊查找视频
    List<Video> findVideoByName(String content);

    int updateVideoPageshot(Video video);

    void  updateVideoName(Video video,String newName);

    void updateVideoIntroduction(Video video,String newIntroduction);

    int addLikeNum(int videoId, Long likeNum);

    int setStarNum(int videoId, Long starNum);

    int addShareNum(int videoId, Long shareNum);

}

package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Tag;
import org.newhome.entity.Tagrecord;
import org.newhome.entity.User;
import org.newhome.entity.Video;
import org.newhome.mapper.TagMapper;
import org.newhome.service.VideoService;
import org.newhome.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    VideoMapper videoMapper;

    @Override
    public int uploadVideo(Video video) {
        int num = videoMapper.insert(video);
        return num;
    }

    @Override
    public int deleteVideo(Video video) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<Video>();
        wrapper.eq(Video::getVideoId,video.getVideoId());
        int num = videoMapper.delete(wrapper);
        return num;

    }

    @Override
    public int deleteVideos(List<Integer> videoidList) {
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<Video>();
        wrapper.in(Video::getVideoId,videoidList);
        int num = videoMapper.delete(wrapper);
        return num;

    }

    @Override
    public void findVideos(Video video) {

    }

    @Override

    public Video findVideobyId(Integer videoid){
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<Video>();
        wrapper.eq(Video::getVideoId, videoid);
        Video video = videoMapper.selectOne(wrapper);
        return video;
    }
    //根据用户id查找视频
    @Override
    public List<Video> findVideoByUser(User user){
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<Video>();
        wrapper.eq(Video::getUserId, user.getUserId());
        return videoMapper.selectList(wrapper);
    }

    @Override
    public List<Video> findVideoByName(String content){
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<Video>();
        wrapper.like(content!=null,Video::getVideoName, content).or()
                .or()
                .like(content!=null,Video::getIntroduction, content);
        return videoMapper.selectList(wrapper);
    }

    @Override
    public void  updateVideoName(Video video,String newName){
        LambdaUpdateWrapper<Video> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Video::getVideoId,video.getVideoId())
                .set(Video::getVideoName,newName);
        videoMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public void updateVideoIntroduction(Video video,String newIntroduction){
        LambdaUpdateWrapper<Video> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Video::getVideoId,video.getVideoId())
                .set(Video::getIntroduction,newIntroduction);
        videoMapper.update(null, lambdaUpdateWrapper);

    }

    @Override
    public int addLikeNum(int videoId, Long likeNum) {
        LambdaUpdateWrapper<Video> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Video::getVideoId, videoId)
                .set(Video::getLikeNum, likeNum);
        return videoMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public int addStarNum(int videoId, Long starNum) {
        LambdaUpdateWrapper<Video> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Video::getVideoId, videoId)
                .set(Video::getStarNum, starNum);
        return videoMapper.update(null, lambdaUpdateWrapper);
    }

    @Override
    public int addShareNum(int videoId, Long shareNum) {
        LambdaUpdateWrapper<Video> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Video::getVideoId, videoId)
                .set(Video::getShareNum, shareNum);
        return videoMapper.update(null, lambdaUpdateWrapper);
    }
}





package org.newhome.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.newhome.entity.Video;
import org.newhome.service.VideoService;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/homepage")
@Slf4j
@Api(value = "视频管理")
@ApiSupport(author = "wyx")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 上传视频
     **/
    @ApiOperation("上传视频")
    @PostMapping("/uploadVideo")
    public ResultBean<Integer> uploadVideo(@RequestBody @Validated Video video) {
        videoService.uploadVideo(video);

        ResultBean<Integer> result = new ResultBean<>();
        result.setMsg("成功");
        result.setCode(ResultBean.SUCCESS);
        result.setData(null);
        return result;
    }

    /**
     * 删除视频
     **/
    @ApiOperation("删除视频")
    @DeleteMapping("/deleteVideo")
    public ResultBean<Integer> deleteVideo(@RequestBody @Validated Integer videoid) {
        videoService.deleteVideo(videoid);

        ResultBean<Integer> result = new ResultBean<>();
        result.setMsg("成功");
        result.setCode(ResultBean.SUCCESS);
        result.setData(null);
        return result;
    }

    /**
     * 批量删除视频
     **/
    @ApiOperation("批量删除视频")
    @DeleteMapping("/deleteVideos")
    public ResultBean<Integer> deleteVideos(@RequestBody @Validated List<Integer> videoIdList) {
        videoService.deleteVideos(videoIdList);

        ResultBean<Integer> result = new ResultBean<>();
        result.setMsg("成功");
        result.setCode(ResultBean.SUCCESS);
        result.setData(null);
        return result;
    }

    /**
     * 查询视频
     **/
    @ApiOperation("查询视频")
    @GetMapping("/findVideos")
    public ResultBean<List<Video>> findVideos(Integer videoid, String videoname, Integer userid,
                                                  String introduction, String createTime, Long likenum,
                                                  Long starNum, Long shareNum) throws ParseException {
        Video video = new Video();
        video.setVideoId(videoid);
        video.setVideoName(videoname);
        video.setUserId(userid);
        video.setIntroduction(introduction);
        video.setLikeNum(likenum);
        video.setStarNum(starNum);
        video.setShareNum(shareNum);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newTime = format.parse(createTime);
            video.setCreateTime(newTime); //Sun Feb 02 02:02:02 CST 2020
        } catch (ParseException e) {
            e.printStackTrace();
        }
        videoService.findVideos(video);

        ResultBean<List<Video>> result = new ResultBean<>();
        result.setMsg("成功");
        result.setCode(ResultBean.SUCCESS);
        result.setData(null);
        return result;
    }

}

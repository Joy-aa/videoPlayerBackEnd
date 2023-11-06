package org.newhome.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.Tagrecord;
import org.newhome.entity.User;
import org.newhome.entity.Video;
import org.newhome.res.VideoRes;
import org.newhome.service.UserService;
import org.newhome.service.VideoService;
import org.newhome.util.QiNiuUtil;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
@RequestMapping("/video")
@Slf4j
@Api(tags = "视频管理")

@ApiSupport(author = "wyx")
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    UserService userService;


    @ApiOperation("上传视频的token")
    @PostMapping("getVideoToken")
    public String getVideoToken() {
        String accessKey = "cjph6i_nsZJwxelLwEqaj4dlknNKEI94oVpRuRQF";
        String secretKey = "ulCAHAVVI62MuiwlL9yHg-FNrbtRw5dZqJb1SyiL";
        String bucket = "new-web-shortvideo";

        Auth auth = Auth.create(accessKey, secretKey);

        return auth.uploadToken(bucket);
    }


//    @ApiOperation("下载视频的url")
//    @GetMapping("getDownLoadVideoUrl")
//    public String getDownLoadVideoUrl(String fileName) {
//        String accessKey = "cjph6i_nsZJwxelLwEqaj4dlknNKEI94oVpRuRQF";
//        String secretKey = "ulCAHAVVI62MuiwlL9yHg-FNrbtRw5dZqJb1SyiL";
//        String bucketDomain  = "http://s3604nf5a.hn-bkt.clouddn.com";
//        String finalUrl ="";
//
//        String publicUrl = String.format("%s/%s", bucketDomain, fileName);
//        Auth auth = Auth.create(accessKey, secretKey);
//        long expireInSeconds = 3600; // 1小时，可以自定义链接过期时间
//        finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
//        return finalUrl;
//    }


    @ApiOperation("删除七牛云文件")
    @PostMapping("deleteQiniu")
    public ResultBean<Integer> deleteQiniu(String fileName) {
        ResultBean<Integer> result = new ResultBean<>();
        String accessKey = "cjph6i_nsZJwxelLwEqaj4dlknNKEI94oVpRuRQF";
        String secretKey = "ulCAHAVVI62MuiwlL9yHg-FNrbtRw5dZqJb1SyiL";
        String bucketName  = "new-web-shortvideo";
        try {
            Configuration cfg = new Configuration(Region.region0());
            String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
            Auth auth = Auth.create(accessKey, secretKey);
            BucketManager bucketManager = new BucketManager(auth, cfg);
            try {
                bucketManager.delete(bucketName, encodedFileName);
            } catch (QiniuException ex) {
                //如果遇到异常，说明删除失败
                System.err.println(ex.code());
                System.err.println(ex.response.toString());
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        } catch (UnsupportedEncodingException e) {
            // 处理编码异常
            e.printStackTrace();
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        result.setCode(ResultBean.SUCCESS);
        result.setData(null);
        return result;
    }
    /**
     * 上传视频
     **/
    @ApiOperation("上传视频")
    @PostMapping("uploadVideo")
    public ResultBean<Video> uploadVideo(String videoName, Integer userId,String introduction) {
        ResultBean<Video> result = new ResultBean<>();
        Video video = new Video();
        video.setVideoName(videoName);
        video.setUserId(userId);
        video.setIntroduction(introduction);
        video.setShareNum(new Long(0));
        video.setStarNum(new Long(0));
        video.setLikeNum(new Long(0));
        String fileName = videoName + ".mp4";
        video.setVideoPath(QiNiuUtil.getDownLoadVideoUrl(fileName));
        Date time = new Date();
        video.setCreateTime(time);

        int res = videoService.uploadVideo(video);
        if(res!=0){
            result.setMsg("视频上传成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(video);
        }
        else{
            result.setMsg("视频上传失败");
            result.setCode(ResultBean.FAIL);
            result.setData(video);
        }

        return result;
    }

    /**
     * 删除视频
     **/
    @ApiOperation("删除视频")
    @DeleteMapping("deleteVideo")
    public ResultBean<Integer> deleteVideo(Integer videoid) {
        ResultBean<Integer> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoid);
        if(video == null){
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            int res = videoService.deleteVideo(video);
            if(res!=-1){
                result.setMsg("视频删除成功");
                result.setCode(ResultBean.SUCCESS);
                result.setData(null);
            }
            else{
                result.setMsg("视频删除失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    /**
     * 批量删除视频
     **/
    @ApiOperation("批量删除视频")
    @DeleteMapping("deleteVideos")
    public ResultBean<Integer> deleteVideos(@RequestParam List<Integer> videoIdList) {
        ResultBean<Integer> result = new ResultBean<>();
        int res = videoService.deleteVideos(videoIdList);
        result.setMsg("视频删除成功");
        result.setCode(ResultBean.SUCCESS);
        result.setData(null);

        return result;
    }

    /**
     * 查询视频
     **/
    @ApiOperation("查询视频")
    @GetMapping("findVideos")
    public ResultBean<Video> findVideos(Integer videoId) throws ParseException {
        ResultBean<Video> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        if(video == null) {
            result.setMsg("视频不存在");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        result.setMsg("成功");
        result.setData(video);
        return result;
    }

    @ApiOperation("根据用户查询视频")
    @GetMapping("findVideoByUser")
    public ResultBean<List<Video>> findVideoByUser(Integer userid){
        ResultBean<List<Video>> result= new ResultBean<>();
        User user = userService.findById(userid);
        if(user == null){
            result.setMsg("用户不存在");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            List<Video> videoList = videoService.findVideoByUser(user);
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(videoList);
        }
        return result;
    }
    @ApiOperation("根据视频名和简介模糊查询")
    @GetMapping("findVideoByName")

    public ResultBean<List<VideoRes>> findVideoByName(String content){
        ResultBean<List<VideoRes>> result= new ResultBean<>();
        List<Video> videoList= videoService.findVideoByName(content);
        List<VideoRes> videoResList = new ArrayList<>();
        for (Video video: videoList) {
            User user = userService.findById(video.getUserId());
            VideoRes videoRes = new VideoRes(video);
            videoRes.setUsername(user.getUsername());
            videoResList.add(videoRes);
        }
        result.setMsg("查询成功");
        result.setCode(ResultBean.SUCCESS);
        result.setData(videoResList);
        return result;
    }

    @CrossOrigin
    @ApiOperation("修改视频名称")
    @PostMapping("updateVideoName")
    public ResultBean<Integer> updateVideoName(Integer videoid ,String newName) {
        ResultBean<Integer> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoid);
        if (video == null) {
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        } else {
            videoService.updateVideoName(video, newName);
            result.setMsg("名称修改成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("添加视频likenum")
    @PostMapping("addLikeNum")
    public ResultBean<Video> addLikeNum(Integer videoId) {
        ResultBean<Video> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        if (video == null) {
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        } else {
            video.setLikeNum(video.getLikeNum()+1);
            int flag = videoService.addLikeNum(videoId, video.getLikeNum());
            if(flag != 0) {
                result.setMsg("添加喜爱数目成功");
                result.setData(video);
            }
            else {
                result.setMsg("添加喜爱数目失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("添加视频starnum")
    @PostMapping("addStarNum")
    public ResultBean<Video> addStarNum(Integer videoId) {
        ResultBean<Video> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        if (video == null) {
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        } else {
            video.setStarNum(video.getStarNum()+1);
            int flag = videoService.setStarNum(videoId, video.getStarNum());
            if(flag != 0) {
                result.setMsg("添加收藏数目成功");
                result.setData(video);
            }
            else {
                result.setMsg("添加收藏数目失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("减少视频starnum")
    @PostMapping("subStarNum")
    public ResultBean<Video> subStarNum(Integer videoId) {
        ResultBean<Video> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        if (video == null) {
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        } else {
            video.setStarNum(video.getStarNum()-1);
            int flag = videoService.setStarNum(videoId, video.getStarNum());
            if(flag != 0) {
                result.setMsg("减少收藏数目成功");
                result.setData(video);
            }
            else {
                result.setMsg("减少收藏数目失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("添加视频sharenum")
    @PostMapping("addShareNum")
    public ResultBean<Video> addShareNum(Integer videoId) {
        ResultBean<Video> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        if (video == null) {
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        } else {
            video.setShareNum(video.getShareNum()+1);
            int flag = videoService.addShareNum(videoId, video.getShareNum());
            if(flag != 0) {
                result.setMsg("添加转发数目成功");
                result.setData(video);
            }
            else {
                result.setMsg("添加转发数目失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("修改视频简介")
    @PostMapping("updateVideoIntroduction")
    public ResultBean<Integer> updateVideoIntroduction(Integer videoid ,String newIntroduction) {
        ResultBean<Integer> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoid);
        if (video == null) {
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        } else {
            videoService.updateVideoIntroduction(video, newIntroduction);
            result.setMsg("简介修改成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(null);
        }
        return result;
    }

}

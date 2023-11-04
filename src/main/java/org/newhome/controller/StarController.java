package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Result;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.Star;
import org.newhome.entity.User;
import org.newhome.entity.Video;
import org.newhome.req.AddStarReq;
import org.newhome.req.DeleteStarReq;
import org.newhome.req.DeleteAllStarReq;
import org.newhome.res.StarRes;
import org.newhome.res.StarsRes;
import org.newhome.service.StarService;
import org.newhome.service.UserService;
import org.newhome.service.VideoService;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Api(tags = "收藏夹")
@RestController
@RequestMapping("/star")
public class StarController {

    @Autowired
    StarService starService;

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @CrossOrigin
    @ApiOperation("增加收藏记录")
    @PostMapping("add")
    @FilterAnnotation(url="/star/add", type = FilterType.auth)
    public ResultBean<StarRes> Add(AddStarReq addStarReq) {
        ResultBean<StarRes> result = new ResultBean<>();
        User user = userService.findById(addStarReq.getUserId());
        if(user == null) {
            result.setMsg("用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        Video video  = videoService.findVideobyId(addStarReq.getVideoId());
        if(video == null) {
            result.setMsg("视频不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        Star star = starService.getOne(addStarReq.getUserId(), addStarReq.getVideoId());
        if(star == null) {
            StarRes starRes = new StarRes();
            star = new Star();
            star.setUserId(addStarReq.getUserId());
            star.setVideoid(addStarReq.getVideoId());
            starRes.setStar(starService.addStar(star));
            result.setData(starRes);
            result.setMsg("添加一条收藏记录");
        }
        else {
            result.setMsg("视频已收藏！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("删除收藏记录")
    @PostMapping("delete")
    @FilterAnnotation(url="/star/delete", type = FilterType.auth)
    public ResultBean<StarRes> delete(DeleteStarReq deleteStarReq) {
        ResultBean<StarRes> result = new ResultBean<>();
        Star star = starService.getById(deleteStarReq.getStarId());
        if(star == null) {
            result.setMsg("该记录不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            StarRes starRes = new StarRes();
            if(starService.delete(deleteStarReq.getStarId()) != 0) {
                starRes.setStar(star);
                result.setData(starRes);
                result.setMsg("删除收藏记录成功");
            }
            else{
                result.setMsg("删除收藏记录失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("删除用户收藏记录")
    @PostMapping("deleteAll")
    @FilterAnnotation(url="/star/deleteAll", type = FilterType.auth)
    public ResultBean<StarRes> deleteAll(DeleteAllStarReq req) {
        ResultBean<StarRes> result = new ResultBean<>();
        User user = userService.findById(req.getUserId());
        if(user == null) {
            result.setMsg("该用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            if(starService.deleteAll(req.getUserId()) != 0) {
                result.setData(null);
                result.setMsg("删除收藏记录成功");
            }
            else{
                result.setMsg("删除收藏记录失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("获取用户收藏记录")
    @PostMapping("getAll")
    @FilterAnnotation(url="/star/getAll", type = FilterType.auth)
    public ResultBean<StarsRes> getAll(DeleteAllStarReq req) {
        ResultBean<StarsRes> result = new ResultBean<>();
        User user = userService.findById(req.getUserId());
        if(user == null) {
            result.setMsg("该用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            StarsRes stars = new StarsRes();
            stars.setStars(starService.getAll(req.getUserId()));
            if(!CollectionUtils.isEmpty(stars.getStars())) {
                result.setData(stars);
                result.setMsg("获取收藏记录成功");
            }
            else{
                result.setMsg("获取收藏记录失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("用户是否已收藏该视频")
    @PostMapping("isStar")
    @FilterAnnotation(url="/star/isStar", type = FilterType.auth)
    public ResultBean<StarRes> getAll(int userId, int videoId) {
        ResultBean<StarRes> result = new ResultBean<>();
        User user = userService.findById(userId);
        if(user == null) {
            result.setMsg("该用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        Video video = videoService.findVideobyId(videoId);
        if(video == null) {
            result.setMsg("该视频不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            StarRes starRes = new StarRes();
            starRes.setStar(starService.getOne(userId, videoId));
            if(starRes.getStar() == null) {
                result.setMsg("该视频不存在！");
                result.setCode(ResultBean.NO_PERMISSION);
                result.setData(null);
            }
            else{
                result.setMsg("获取收藏记录");
                result.setData(starRes);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("获取用户收藏的视频")
    @GetMapping("getUserStarVideo")
    @FilterAnnotation(url="/star/getUserStarVideo", type = FilterType.auth)
    public ResultBean<List<Video>> getUserStarVideo(Integer userId ) {
        ResultBean<List<Video>> result = new ResultBean<>();
        User user = userService.findById(userId);
        if(user == null) {
            result.setMsg("该用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            List<Video> videoList = new ArrayList<>();
            List<Star> stars =starService.getAll(userId);
            for(Star star : stars){
                Video video = videoService.findVideobyId(star.getVideoid());
                if(video!=null){
                    videoList.add(video);
                }
            }
            if(!videoList.isEmpty()) {
                result.setData(videoList);
                result.setMsg("获取用户收藏的视频成功");
            }
            else{
                result.setMsg("获取用户收藏的视频失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }
}

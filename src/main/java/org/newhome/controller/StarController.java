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
import org.newhome.res.StarRes;
import org.newhome.service.StarService;
import org.newhome.service.UserService;
import org.newhome.service.VideoService;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        StarRes starRes = new StarRes();
        Star star = new Star();
        star.setUserId(addStarReq.getUserId());
        star.setVideoid(addStarReq.getVideoId());
        starRes.setStar(starService.addStar(star));
        result.setData(starRes);
        result.setMsg("添加一条收藏记录");
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

}

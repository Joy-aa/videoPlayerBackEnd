package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.entity.*;
import org.newhome.req.RelationReq;
import org.newhome.service.*;
import org.newhome.util.DataGenerator;
import org.newhome.util.MD5Util;
import org.newhome.util.QiNiuUtil;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.newhome.util.MD5Util.formPassToDBPass;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/tagrecord")
@Api(tags = "视频与tag记录")
public class TagrecordController {
    @Autowired
    TagrecordService tagrecordService;
    @Autowired
    VideoService videoService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;

    @CrossOrigin
    @ApiOperation("新增视频标签")
    @PostMapping("addTagrecord")
    public ResultBean<Tagrecord> addTagrecord(Integer videoId, Integer tagID) {
        ResultBean<Tagrecord> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        Tag tag = tagService.findTagById(tagID);
        if(video == null){
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if(tag == null){
            result.setMsg("Tag不正确，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            Boolean res =  tagrecordService.isTaginVideo(video , tag);
            if(res){
                result.setMsg("视频已有该标签，请勿重复添加");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
            else{
                Tagrecord tagrecord = tagrecordService.addTagrecord(video,tag);
                if(tagrecord != null){
                    result.setMsg("新增标签成功");
                    result.setCode(ResultBean.SUCCESS);
                    result.setData(tagrecord);
                }
                else{
                    result.setMsg("新增标签失败");
                    result.setCode(ResultBean.FAIL);
                    result.setData(null);
                }
            }

        }
        return result;

    }
    @CrossOrigin
    @ApiOperation("删除视频标签")
    @DeleteMapping("deleteTagrecord")
    public ResultBean<Tagrecord> deleteTagrecord(Integer videoId, Integer tagID) {
        ResultBean<Tagrecord> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        Tag tag = tagService.findTagById(tagID);

        if(video == null){
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if(tag == null){
            result.setMsg("Tag不正确，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            //判断视频是否有该标签
            Boolean res =  tagrecordService.isTaginVideo(video , tag);
            if(res){
                int ans = tagrecordService.deleteTagrecord(video,tag);
                if(ans!=-1){
                String msg = String.format("成功将标签 %s 从视频移除",tag.getTagName());
                result.setMsg(msg);
                result.setCode(ResultBean.FAIL);
                result.setData(null);
             }
            }
            else{
                result.setMsg("视频没有此标签，请确认后重试");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }

        }
        return result;
    }
    @CrossOrigin
    @ApiOperation("查看视频所有标签")
    @GetMapping("findTagrecord")
    public ResultBean<List<Tagrecord>> findTagrecord(Integer videoId) {
        ResultBean<List<Tagrecord>> result = new ResultBean<>();
        Video video = videoService.findVideobyId(videoId);
        if(video == null){
            result.setMsg("视频不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            List<Tagrecord> tagrecordList = tagrecordService.findTagrecord(video);
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(tagrecordList);
        }
        return result;

    }
    @CrossOrigin
    @ApiOperation("查看标签所有视频")
    @GetMapping("findVideoByTag")
    public ResultBean<List<Video>> findVideoByTag(Integer tagId) {
        ResultBean<List<Video>> result = new ResultBean<>();
        Tag tag = tagService.findTagById(tagId);
        if(tag == null){
            result.setMsg("标签不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            List<Video> videoList = new ArrayList<>();
            List<Tagrecord> tagrecordList = tagrecordService.findVideoByTag(tag);
            for (Tagrecord tagrecord1: tagrecordList) {
                int vid = tagrecord1.getVideoId();
                Video video = videoService.findVideobyId(vid);
                String pageFilename = video.getIntroduction().substring(0, video.getIntroduction().indexOf('.')) + ".jpg";
                String pageshotUrl = QiNiuUtil.getDownLoadVideoUrl(pageFilename);
                video.setPageshot(pageshotUrl);
                videoList.add(video);
            }
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(videoList);
        }
        return result;

    }

    @CrossOrigin
    @ApiOperation("查看标签所有视频对应的用户")
    @GetMapping("findUserOfVideoByTag")
    public ResultBean<List<String>> findUserOfVideoByTag(Integer tagId) {
        ResultBean<List<String>> result = new ResultBean<>();
        Tag tag = tagService.findTagById(tagId);
        if(tag == null){
            result.setMsg("标签不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            List<Video> videoList = new ArrayList<>();
            List<Tagrecord> tagrecordList = tagrecordService.findVideoByTag(tag);
            List<String> userList = new ArrayList<>();
            for (Tagrecord tagrecord1: tagrecordList) {
                int vid = tagrecord1.getVideoId();
                Video video = videoService.findVideobyId(vid);
                if (video == null) {
                    continue;
                }
                videoList.add(video);
                int uid = video.getUserId();
                User user = userService.findById(uid);
                userList.add(user.getUsername());
            }
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(userList);
        }
        return result;

    }

    @CrossOrigin
    @ApiOperation("生成视频与标签的对应")
    @GetMapping("generate")
    public void userGenerate() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        for(int i = 64; i <= 234; i++) {
            int tagId = random.nextInt(14) + 1;
            int videoId = i;
            ResultBean<Tagrecord> resultBean = addTagrecord(videoId, tagId);
            if(resultBean.getCode() != 0) {
                System.out.println(resultBean.getMsg());
                break;
            }
        }
//        int tagId = random.nextInt(14) + 1;

    }
}

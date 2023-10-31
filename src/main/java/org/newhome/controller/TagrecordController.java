package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.entity.Relation;
import org.newhome.entity.Tag;
import org.newhome.entity.Tagrecord;
import org.newhome.entity.Video;
import org.newhome.req.RelationReq;
import org.newhome.service.*;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/tagrecord")
@Api(tags = "视频与tag记录")
public class TagrecordController {
    @Autowired
    TagrecordService tagrecordService;
    @Autowired
    VideoService videoService;
    @Autowired
    TagService tagService;

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
    public ResultBean<List<Tagrecord>> findVideoByTag(Integer tagId) {
        ResultBean<List<Tagrecord>> result = new ResultBean<>();
        Tag tag = tagService.findTagById(tagId);
        if(tag == null){
            result.setMsg("标签不存在，请重新确认");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            List<Tagrecord> tagrecordList = tagrecordService.findVideoByTag(tag);
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(tagrecordList);
        }
        return result;

    }
}

package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.Comment;
import org.newhome.entity.User;
import org.newhome.entity.Video;
import org.newhome.req.AddCommentReq;
import org.newhome.req.DeleteCommentReq;
import org.newhome.req.GetCommentReq;
import org.newhome.res.CommentRes;
import org.newhome.service.CommentService;
import org.newhome.service.UserService;
import org.newhome.service.VideoService;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Api(tags = "评论")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @Autowired
    CommentService commentService;

    @CrossOrigin
    @ApiOperation("增加评论")
    @PostMapping("add")
    @FilterAnnotation(url="/comment/add",type = FilterType.auth)
    public ResultBean<CommentRes> addComment(AddCommentReq req) {
        ResultBean<CommentRes> result = new ResultBean<>();
        User user = userService.findById(req.getUserId());
        if(user == null) {
            result.setMsg("用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        Video video  = videoService.findById(req.getVideoId());
        if(video == null) {
            result.setMsg("视频不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        CommentRes commentRes = new CommentRes();
        Comment comment = new Comment();
        comment.setVideoId(req.getVideoId());
        comment.setUserId(req.getUserId());
        comment.setContent(req.getContent());
        comment.setCreateTime(req.getCreateTime());
        comment.setLikeNum(0L);
        commentRes.setComment(comment);
        if(commentService.addComment(comment) != 0) {
            result.setMsg("插入浏览记录成功");
            result.setData(commentRes);
        }
        else{
            result.setData(null);
            result.setMsg("插入评论失败");
            result.setCode(ResultBean.FAIL);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("展示评论")
    @PostMapping("showAll")
    @FilterAnnotation(url="/comment/showAll",type = FilterType.auth)
    public ResultBean<CommentRes> showComments(GetCommentReq req) {
        ResultBean<CommentRes> result = new ResultBean<>();

        return result;
    }

    @CrossOrigin
    @ApiOperation("点赞评论")
    @PostMapping("like")
    @FilterAnnotation(url="/comment/like",type = FilterType.auth)
    public ResultBean<CommentRes> likeComment(DeleteCommentReq req) {
        ResultBean<CommentRes> result = new ResultBean<>();

        return result;
    }

    @CrossOrigin
    @ApiOperation("删除评论")
    @PostMapping("delete")
    @FilterAnnotation(url="/comment/delete",type = FilterType.auth)
    public ResultBean<CommentRes> deleteComment(DeleteCommentReq req) {
        ResultBean<CommentRes> result = new ResultBean<>();

        return result;
    }

}

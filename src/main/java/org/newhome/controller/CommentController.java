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
import org.newhome.res.CommentsRes;
import org.newhome.service.CommentService;
import org.newhome.service.UserService;
import org.newhome.service.VideoService;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        Video video  = videoService.findVideobyId(req.getVideoId());
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
            result.setMsg("插入评论成功");
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
    public ResultBean<CommentsRes> showComments(GetCommentReq req) {
        ResultBean<CommentsRes> result = new ResultBean<>();
        Video video  = videoService.findVideobyId(req.getVideoId());
        if(video == null) {
            result.setMsg("视频不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        CommentsRes commentsRes = new CommentsRes();
        List<Comment> comments = commentService.getComments(req.getVideoId());
        if(!CollectionUtils.isEmpty(comments)) {
            commentsRes.setComments(comments);
            result.setMsg("获取多条评论");
            result.setData(commentsRes);
        }
        else{
            result.setData(null);
            result.setMsg("该条视频没有评论");
            result.setCode(ResultBean.FAIL);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("点赞评论")
    @PostMapping("like")
    @FilterAnnotation(url="/comment/like",type = FilterType.auth)
    public ResultBean<CommentRes> likeComment(Integer commentId, Long likeNum) {
        ResultBean<CommentRes> result = new ResultBean<>();
        CommentRes commentRes = new CommentRes();
        Comment comment = commentService.getComment(commentId);
        if(comment == null) {
            result.setMsg("评论不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            comment.setLikeNum(likeNum+1);
            int flag = commentService.updateLikeNum(comment);
            commentRes.setComment(comment);
            if(flag != 0) {
                result.setMsg("评论点赞数目已增加");
                result.setData(commentRes);
            }
            else{
                result.setMsg("评论不存在！");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("取消点赞评论")
    @PostMapping("dislike")
    @FilterAnnotation(url="/comment/dislike",type = FilterType.auth)
    public ResultBean<CommentRes> dislikeComment(Integer commentId, Long likeNum) {
        ResultBean<CommentRes> result = new ResultBean<>();
        CommentRes commentRes = new CommentRes();
        Comment comment = commentService.getComment(commentId);
        if(comment == null) {
            result.setMsg("评论不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else{
            if(likeNum > 0) {
                comment.setLikeNum(likeNum-1);
            }
            int flag = commentService.updateLikeNum(comment);
            commentRes.setComment(comment);
            if(flag != 0) {
                result.setMsg("评论点赞数目已减少");
                result.setData(commentRes);
            }
            else{
                result.setMsg("评论不存在！");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("删除评论")
    @PostMapping("delete")
    @FilterAnnotation(url="/comment/delete",type = FilterType.auth)
    public ResultBean<CommentRes> deleteComment(DeleteCommentReq req) {
        ResultBean<CommentRes> result = new ResultBean<>();
        CommentRes commentRes = new CommentRes();
        Comment comment = commentService.getComment(req.getCommentId());
        if(comment == null) {
            result.setMsg("评论不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        int flag = commentService.deleteComment(req.getCommentId());
        commentRes.setComment(comment);
        if(flag != 0) {
            result.setMsg("评论已删除");
            result.setData(commentRes);
        }
        else{
            result.setMsg("评论删除失败！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("添加评论到数据库")
    @GetMapping("commentGenerate")
    public ResultBean<List<String[]>> commentGenerate() {
        ResultBean<List<String[]> > result = new ResultBean<>();

        String csvFile = "D:\\IDEAProjects\\videoPlayerBackEnd\\src\\main\\java\\org\\newhome\\controller\\comments1.csv";
        String line;
        String csvSplitBy = ",";
        Charset charset = StandardCharsets.UTF_8;
        List<User> userList = userService.getAllUsers();
        List<Video> videoList = videoService.findVideos();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), charset))) {
            List<String[]> data = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                data.add(row);
            }
            result.setData(data);
            Random random = new Random();

            // 定义日期格式
            String pattern = "yyyy/MM/dd HH:mm";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

            // 处理读取到的数据
            int i=0;
            for (String[] row : data) {
                if(i>0) {
                    Comment comment = new Comment();
                    String content = row[0];
                    comment.setContent(content);

                    try {
                        // 将时间字符串解析为 Date 对象
                        if (row.length < 2) {
                            comment.setCreateTime(new Date());
                        } else {
                            Date date = dateFormat.parse(row[1]);
                            // 打印解析后的 Date 对象
                            System.out.println("解析后的 Date 对象: " + date);
                            comment.setCreateTime(date);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int min = 0;
                    int max = userList.size() - 1;
                    int randomInRange = random.nextInt(max - min + 1) + min;
                    comment.setUserId(userList.get(randomInRange).getUserId());

                    max = videoList.size() - 1;
                    randomInRange = random.nextInt(max - min + 1) + min;
                    comment.setVideoId(videoList.get(randomInRange).getVideoId());

                    max = 100;
                    randomInRange = random.nextInt(max - min + 1) + min;
                    comment.setLikeNum((long) randomInRange);
                    commentService.addComment(comment);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}

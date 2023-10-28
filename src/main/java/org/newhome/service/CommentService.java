package org.newhome.service;

import org.newhome.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-10-27 15:58:38
*/
public interface CommentService extends IService<Comment> {
    int addComment(Comment comment);

    Comment getComment(int commentId);
    List<Comment> getComments(int videoId);

    int deleteComment(int commentId);

    int updateLikeNum(Comment comment);

}

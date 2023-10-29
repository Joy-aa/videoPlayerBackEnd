package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Comment;
import org.newhome.service.CommentService;
import org.newhome.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-10-27 15:58:38
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public Comment getComment(int commentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>();
        wrapper.eq(Comment::getCommentId, commentId);
        return commentMapper.selectOne(wrapper);
    }

    @Override
    public List<Comment> getComments(int videoId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>();
        wrapper.eq(Comment::getVideoId, videoId);
        return commentMapper.selectList(wrapper);
    }

    @Override
    public int deleteComment(int commentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>();
        wrapper.eq(Comment::getCommentId, commentId);
        return commentMapper.delete(wrapper);
    }

    @Override
    public int updateLikeNum(Comment comment) {
        LambdaUpdateWrapper<Comment> wrapper = new LambdaUpdateWrapper<Comment>();
        wrapper.eq(Comment::getCommentId, comment.getCommentId())
                .set(Comment::getLikeNum, comment.getLikeNum());
        return commentMapper.update(null, wrapper);
    }
}





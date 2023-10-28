package org.newhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Comment;
import org.newhome.service.CommentService;
import org.newhome.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}





package org.newhome.service.impl;

import org.newhome.entity.Comment;
import org.newhome.mapper.CommentMapper;
import org.newhome.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}

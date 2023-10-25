package org.newhome.info;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentInfo {

    private Integer commentId;

    private Integer videoId;

    private Integer userId;

    private Long likeNum;

    private String content;

    private LocalDateTime createTime;
}

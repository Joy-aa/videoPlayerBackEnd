package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "video_id", type = IdType.AUTO)
    private Integer videoId;

    private String videoName;

    private Integer userId;

    private LocalDateTime createTime;

    private String pageshot;

    private Long likenum;

    private Long starNum;

    private Long shareNum;

    private String videoPath;

    private String introduction;


}

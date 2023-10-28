package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer videoId;

    /**
     * 
     */
    private String videoName;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String pageshot;

    /**
     * 
     */
    private Long likeNum;

    /**
     * 
     */
    private Long starNum;

    /**
     * 
     */
    private Long shareNum;

    /**
     * 
     */
    private String videoPath;

    /**
     * 
     */
    private String introduction;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName star
 */
@TableName(value ="star")
@Data
public class Star implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer starId;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer videoid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
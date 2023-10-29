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
 * @TableName history
 */
@TableName(value ="history")
@Data
public class History implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer historyId;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer videoId;

    /**
     * 
     */
    private Date watchTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
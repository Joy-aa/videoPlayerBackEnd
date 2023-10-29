package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tagrecord
 */
@TableName(value ="tagrecord")
@Data
public class Tagrecord implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer recordId;

    /**
     * 
     */
    private Integer tagId;

    /**
     * 
     */
    private Integer videoId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
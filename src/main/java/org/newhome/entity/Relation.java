package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName relation
 */
@TableName(value ="relation")
@Data
public class Relation implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer relationId;

    /**
     * 
     */
    private Integer user1Id;

    /**
     * 
     */
    private Integer user2Id;

    /**
     * 
     */
    private Integer kind;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "relation_id", type = IdType.AUTO)
    private Integer relationId;

    private Integer user1Id;

    private Integer user2Id;

    private Integer kind;


}

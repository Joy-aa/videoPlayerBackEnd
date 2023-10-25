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
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "history_id", type = IdType.AUTO)
    private Integer historyId;

    private Integer userId;

    private Integer vedioId;

    private LocalDateTime watchTime;


}

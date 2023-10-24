package org.newhome.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.newhome.info.AlgorithmInfo;
import org.newhome.util.MyBeanUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Algorithm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer algoId;

    private String algoName;

    public Algorithm(){}

    public Algorithm(AlgorithmInfo algorithmInfo){
        MyBeanUtils.copyProperties(algorithmInfo, this);
    }

    public AlgorithmInfo change(){
        AlgorithmInfo algorithmInfo = new AlgorithmInfo();
        MyBeanUtils.copyProperties(this, algorithmInfo);
        return algorithmInfo;
    }
}

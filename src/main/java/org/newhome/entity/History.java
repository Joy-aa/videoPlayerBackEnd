package org.newhome.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.newhome.info.HistoryInfo;
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
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer historyId;

    private String historyName;

    private Integer algoId;

    private String algoName;

    private String username;

    private Integer datasetId;

    private String datasetName;

    private String modelPath;

    //    private Timestamp dateTime;
    private String dateTime;

    public History(){}

    public History(HistoryInfo historyInfo){
        MyBeanUtils.copyProperties(historyInfo, this);
    }

    public HistoryInfo change(){
        HistoryInfo historyInfo = new HistoryInfo();
        MyBeanUtils.copyProperties(this, historyInfo);
        return historyInfo;
    }


}

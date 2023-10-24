package org.newhome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;
import lombok.Data;
import org.newhome.info.PictureDataInfo;
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
public class PictureData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer pictureId;

    private String pictureUrl;

    @TableField("picture_result")
    private String pictureResult;

    private Integer datasetId;

    public PictureData(){}

    public PictureData(PictureDataInfo pictureDataInfo){
        MyBeanUtils.copyProperties(pictureDataInfo, this);
    }

    public PictureDataInfo change(){
        PictureDataInfo pictureDataInfo = new PictureDataInfo();
        MyBeanUtils.copyProperties(this, pictureDataInfo);
        return pictureDataInfo;
    }


}

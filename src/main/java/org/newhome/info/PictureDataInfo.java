package org.newhome.info;

import lombok.Data;
import org.newhome.entity.PictureData;
import org.newhome.util.MyBeanUtils;

@Data
public class PictureDataInfo {

    private Integer pictureId;

    private String pictureUrl;

    private String pictureResult;

    private Integer datasetId;


    public PictureDataInfo(){}

    public PictureDataInfo(PictureData pictureData){
        MyBeanUtils.copyProperties(pictureData, this);
    }

    public PictureData change(){
        PictureData pictureData = new PictureData();
        MyBeanUtils.copyProperties(this, pictureData);
        return pictureData;
    }
}

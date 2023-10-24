package org.newhome.info;

import lombok.Data;
import org.newhome.entity.Dataset;
import org.newhome.util.MyBeanUtils;

@Data
public class DatasetInfo {

    private Integer datasetId;

    private String username;

    private String datasetName;

    private Integer datasetIspublic;

    public DatasetInfo(){}

    public DatasetInfo(Dataset dataset){
        MyBeanUtils.copyProperties(dataset, this);
    }

    public Dataset change(){
        Dataset dataset = new Dataset();
        MyBeanUtils.copyProperties(this, dataset);
        return dataset;
    }
}

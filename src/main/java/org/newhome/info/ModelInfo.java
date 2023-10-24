package org.newhome.info;

import lombok.Data;
import org.newhome.entity.Model;
import org.newhome.util.MyBeanUtils;

@Data
public class ModelInfo {

    private Integer modelId;

    private String modelName;

    private Integer algoId;

    private String username;

    private Integer datasetId;

    private String modelPath;

    public ModelInfo(){}

    public ModelInfo(Model model){
        MyBeanUtils.copyProperties(model, this);
    }
}

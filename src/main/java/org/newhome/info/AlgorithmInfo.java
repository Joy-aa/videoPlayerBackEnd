package org.newhome.info;

import org.newhome.entity.Algorithm;
import org.newhome.util.MyBeanUtils;

import lombok.Data;

@Data
public class AlgorithmInfo {

    private Integer algoId;

    private String algoName;

    public AlgorithmInfo(){}

    public AlgorithmInfo(Algorithm algorithm){
        MyBeanUtils.copyProperties(algorithm, this);
    }

    public Algorithm change(){
        Algorithm algorithm = new Algorithm();
        MyBeanUtils.copyProperties(this, algorithm);
        return algorithm;
    }
}

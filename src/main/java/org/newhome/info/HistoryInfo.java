package org.newhome.info;

import lombok.Data;
import org.newhome.entity.History;
import org.newhome.util.MyBeanUtils;

@Data
public class HistoryInfo {

    private Integer historyId;

    private String historyName;

    private Integer algoId;

    private String algoName;

    private String username;

    private Integer HistoryId;

    private String modelPath;

//    private Timestamp dateTime;
    private String dateTime;

    private Integer datasetId;

    private String datasetName;

    public HistoryInfo(){}

    public HistoryInfo(History history){
        MyBeanUtils.copyProperties(history, this);
    }

    public History change(){
        History history = new History();
        MyBeanUtils.copyProperties(this, history);
        return history;
    }
}

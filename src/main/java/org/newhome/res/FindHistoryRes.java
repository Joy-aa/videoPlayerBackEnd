package org.newhome.res;

import lombok.Data;
import org.newhome.info.HistoryInfo;

import java.util.List;

@Data
public class FindHistoryRes {
    private List<HistoryInfo> historyInfos;
}

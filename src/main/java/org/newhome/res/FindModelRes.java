package org.newhome.res;

import lombok.Data;
import org.newhome.info.ModelInfo;

import java.util.List;

@Data
public class FindModelRes {
    private List<ModelInfo> modelInfos;
}

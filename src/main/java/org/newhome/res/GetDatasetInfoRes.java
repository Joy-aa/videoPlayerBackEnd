package org.newhome.res;

import lombok.Data;
import org.newhome.info.DatasetInfo;
import org.newhome.info.PictureDataInfo;

import java.util.List;

@Data
public class GetDatasetInfoRes {
    private DatasetInfo datasetInfo;

    private List<PictureDataInfo> pictureDataInfos;
}

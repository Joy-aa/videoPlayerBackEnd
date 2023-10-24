package org.newhome.res;

import lombok.Data;
import org.newhome.info.DatasetInfo;

@Data
public class CreateDatasetRes {
    private boolean succeed;

    private DatasetInfo datasetInfo;
}

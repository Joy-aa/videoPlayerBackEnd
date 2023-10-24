package org.newhome.res;

import lombok.Data;
import org.newhome.info.PictureDataInfo;

import java.util.List;

@Data
public class GetPicturesRes {

    private List<PictureDataInfo> pictures;

}

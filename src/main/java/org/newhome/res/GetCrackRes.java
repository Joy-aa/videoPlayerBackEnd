package org.newhome.res;

import lombok.Data;
import org.newhome.info.CrackInfo;

import java.util.List;

@Data
public class GetCrackRes {

    private List<CrackInfo> crackInfos;

}

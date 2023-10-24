package org.newhome.res;

import lombok.Data;
import org.newhome.info.UserInfo;

import java.util.List;

@Data
public class AllUserRes {
    private List<UserInfo> userInfos;
}

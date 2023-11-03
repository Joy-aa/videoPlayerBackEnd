package org.newhome.res;

import io.swagger.models.auth.In;
import lombok.Data;
import org.newhome.entity.User;

@Data
public class UserRes {
    public UserRes(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.headshot = user.getHeadshot();
        this.introduction = user.getIntroduction();

    }
    private Integer userId;
    private String username;
    private String email;
    private String headshot;
    private String introduction;
    private Integer likeNum;
    private Integer fanNum;
}

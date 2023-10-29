package org.newhome.req;

import lombok.Data;

import java.util.Date;

@Data
public class AddCommentReq {
    private int videoId;
    private int userId;
    private String content;
    private Date createTime;
}

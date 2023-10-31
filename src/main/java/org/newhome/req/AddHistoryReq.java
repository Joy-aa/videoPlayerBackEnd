package org.newhome.req;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
public class AddHistoryReq {
    private int userId;

    private int videoId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date watchTime;
}

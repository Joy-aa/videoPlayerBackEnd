package org.newhome.info;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoInfo {
    private Integer videoId;

    private String videoName;

    private Integer userId;

    private LocalDateTime createTime;

    private String pageshot;

    private Long likenum;

    private Long starNum;

    private Long shareNum;

    private String videoPath;

    private String introduction;
}

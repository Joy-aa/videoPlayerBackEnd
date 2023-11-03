package org.newhome.res;

import io.swagger.models.auth.In;
import lombok.Data;
import org.newhome.entity.User;
import org.newhome.entity.Video;

import java.util.Date;

@Data
public class VideoRes {
    public VideoRes(Video video) {
        this.videoId = video.getVideoId();
        this.videoName = video.getVideoName();
        this.userId = video.getUserId();
        this.createTime = video.getCreateTime();
        this.pageshot = video.getPageshot();
        this.likeNum = video.getLikeNum();
        this.shareNum = video.getShareNum();
        this.starNum = video.getStarNum();
        this.videoPath = video.getVideoPath();
        this.introduction = video.getIntroduction();
    }
    private Integer videoId;
    private String videoName;
    private Integer userId;
    private Date createTime;
    private String pageshot;
    private Long likeNum;
    private Long starNum;
    private Long shareNum;
    private String videoPath;
    private String introduction;

    private String username;
}

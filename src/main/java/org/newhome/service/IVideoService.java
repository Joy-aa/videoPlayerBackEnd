package org.newhome.service;

import org.newhome.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
public interface IVideoService extends IService<Video> {
    int uploadVideo(Video video);

    int deleteVideo(Integer videoid);

    int deleteVideos(List<Integer> videoIdList);

    List<Video> findVideos(Video video);

}

package org.newhome.service.impl;

import org.newhome.entity.Video;
import org.newhome.mapper.VideoMapper;
import org.newhome.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

}

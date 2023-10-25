package org.newhome.service.impl;

import org.newhome.entity.Tag;
import org.newhome.mapper.TagMapper;
import org.newhome.service.ITagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}

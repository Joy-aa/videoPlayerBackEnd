package org.newhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Tag;
import org.newhome.service.TagService;
import org.newhome.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuxin Wang
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2023-10-27 15:58:06
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}





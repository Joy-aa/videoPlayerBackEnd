package org.newhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Star;
import org.newhome.service.StarService;
import org.newhome.mapper.StarMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuxin Wang
* @description 针对表【star】的数据库操作Service实现
* @createDate 2023-10-27 15:58:16
*/
@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star>
    implements StarService{

}





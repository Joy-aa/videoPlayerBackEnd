package org.newhome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.History;
import org.newhome.service.HistoryService;
import org.newhome.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuxin Wang
* @description 针对表【history】的数据库操作Service实现
* @createDate 2023-10-27 15:58:29
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService{

}





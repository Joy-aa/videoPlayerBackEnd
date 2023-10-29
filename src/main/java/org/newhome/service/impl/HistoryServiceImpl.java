package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.History;
import org.newhome.service.HistoryService;
import org.newhome.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【history】的数据库操作Service实现
* @createDate 2023-10-27 15:58:29
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService{
    @Autowired
    HistoryMapper historyMapper;

    @Override
    public List<History> getHistories(int userId) {
        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<History>();
        wrapper.eq(History::getUserId, userId)
                .orderByDesc(History::getWatchTime);
        return historyMapper.selectList(wrapper);
    }

    @Override
    public int addHistory(History history) {
        return historyMapper.insert(history);
    }

    @Override
    public int deleteHistory(int historyId) {
        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<History>();
        wrapper.eq(History::getHistoryId, historyId);
        return historyMapper.delete(wrapper);
    }

//    @Override
//    public int deleteHistories(int userId) {
//        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(History::getUserId, userId);
//        List<History> histories = historyMapper.selectList(wrapper);
//        int num = 0;
//        for(History history:histories) {
//            if(historyMapper.deleteById(history.getHistoryId()) != 0)
//                num ++;
//        }
//        return num;
//    }
}





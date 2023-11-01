package org.newhome.service;

import org.newhome.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【history】的数据库操作Service
* @createDate 2023-10-27 15:58:29
*/
public interface HistoryService extends IService<History> {

    List<History> getHistories(int userId);

    History getOne(int userId, int videoId);

    int addHistory(History history);

    int deleteHistory(int historyId);

    int updateTime(History history);

}

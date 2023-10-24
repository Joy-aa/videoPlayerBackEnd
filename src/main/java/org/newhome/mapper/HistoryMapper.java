package org.newhome.mapper;

import org.newhome.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
public interface HistoryMapper extends BaseMapper<History> {
    public int addHistory(History history);

    public History findHistoryByName(String username, String historyName);

    public List<History> findHistoriesByUser(String username);

    public List<History> findHistoriesByUserAndAlgo(String username, int algoId);

    public List<History> findHistoriesByUserAndDataset(String username, int datasetId);

    public List<History> findHistoriesByUserAndAlgoAndDataset(String username, int algoId, int datasetId);

    public boolean deleteHistory(String username, String historyName);

    public boolean updateHistory(int historyId, String newName);

    public boolean deleteHistoriesByDatasetId(int datasetId);
}

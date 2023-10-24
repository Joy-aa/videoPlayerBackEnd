package org.newhome.service;

import org.newhome.entity.Dataset;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.info.DatasetInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
public interface IDatasetService extends IService<Dataset> {
    public List<DatasetInfo> getUserDatasets(String username);

    public List<DatasetInfo> getPublicDatasets();

    public DatasetInfo findDatasetByUserAndName(String username, String datasetName);

    public List<DatasetInfo> blurredFindDatasetByUserAndName(String username, String datasetName);

    public boolean deleteDataset(int datasetId);

    public DatasetInfo getDatasetInfo(int datasetId);

    public boolean renameDataset(int datasetId, String newName);

    public boolean createDataset(String usernmae, String datasetName, boolean isPublic);

    public DatasetInfo getNewDataset();
}

package org.newhome.service.impl;

import org.newhome.entity.Dataset;
import org.newhome.entity.PictureData;
import org.newhome.info.DatasetInfo;
import org.newhome.mapper.CrackMapper;
import org.newhome.mapper.DatasetMapper;
import org.newhome.mapper.HistoryMapper;
import org.newhome.mapper.PictureDataMapper;
import org.newhome.service.IDatasetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Service
public class DatasetServiceImpl extends ServiceImpl<DatasetMapper, Dataset> implements IDatasetService {

    @Resource
    DatasetMapper datasetMapper;

    @Resource
    PictureDataMapper pictureDataMapper;

    @Resource
    CrackMapper crackMapper;

    @Resource
    HistoryMapper historyMapper;

    public List<DatasetInfo> getUserDatasets(String username) {
        List<DatasetInfo> result = new ArrayList<>();
        List<Dataset> datasets = datasetMapper.getUserDatasets(username);
        for (Dataset dataset : datasets) {
            result.add(dataset.change());
        }
        return result;
    }

    @Override
    public List<DatasetInfo> getPublicDatasets() {
        List<DatasetInfo> result = new ArrayList<>();
        List<Dataset> datasets = datasetMapper.getPublicDatasets();
        for (Dataset dataset : datasets) {
            result.add(dataset.change());
        }
        return result;
    }

    @Override
    public DatasetInfo findDatasetByUserAndName(String username, String datasetName) {
        Dataset dataset = datasetMapper.findDatasetByUserAndName(username, datasetName);
        if (dataset == null)
            return null;
        else
            return dataset.change();
    }
    @Override
    public List<DatasetInfo> blurredFindDatasetByUserAndName(String username, String datasetName) {
        List<Dataset> dataset = datasetMapper.blurredFindDatasetByUserAndName(username, "%" + datasetName + "%");
        if (dataset == null)
            return null;
        else{
            List<DatasetInfo> result = new ArrayList<>();
            for (Dataset d:dataset){
                result.add(d.change());
            }
            return result;
        }
    }

    @Override
    public boolean deleteDataset(int datasetId) {
        List<PictureData> pictureDatas = pictureDataMapper.getPicture(datasetId);
//        boolean succeed = true;
        for(int i = 0; i < pictureDatas.size(); i++) {
            crackMapper.deleteCrackByPictureId(pictureDatas.get(i).getPictureId());
        }
        historyMapper.deleteHistoriesByDatasetId(datasetId);
        pictureDataMapper.deleteDatasetPictures(datasetId);
        boolean succeed = datasetMapper.deleteDataset(datasetId);
        return succeed;
    }

    public DatasetInfo getDatasetInfo(int datasetId) {
        Dataset dataset = datasetMapper.getDatasetInfo(datasetId);
        if(dataset != null){
            return dataset.change();
        }
        return null;
    }

    @Override
    public boolean renameDataset(int datasetId, String newName) {
        return datasetMapper.renameDataset(datasetId, newName);
    }

    @Override
    public boolean createDataset(String usernmae, String datasetName, boolean isPublic) {
        return datasetMapper.createDataset(usernmae, datasetName, isPublic);
    }

    @Override
    public DatasetInfo getNewDataset() {
        return datasetMapper.getNewDataset().change();
    }

}

package org.newhome.mapper;

import org.apache.ibatis.annotations.Param;
import org.newhome.entity.Dataset;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
public interface DatasetMapper extends BaseMapper<Dataset> {
    public List<Dataset> getUserDatasets(@Param("username") String username);

    public List<Dataset> getPublicDatasets();

    public List<Dataset> blurredFindDatasetByUserAndName(String username, String datasetName);

    public boolean deleteDataset(@Param("datasetId") int datasetId);

    public Dataset getDatasetInfo(@Param("datasetId") int datasetId);

    public boolean renameDataset(@Param("datasetId") int datasetId, @Param("newName") String newName);

    public boolean createDataset(@Param("username") String usernmae, @Param("datasetName") String datasetName, @Param("isPublic") boolean isPublic);

    public Dataset getNewDataset();

    public Dataset findDatasetByUserAndName(String username, String datasetName);

}

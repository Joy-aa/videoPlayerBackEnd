package org.newhome.mapper;

import org.apache.ibatis.annotations.Param;
import org.newhome.entity.PictureData;
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
public interface PictureDataMapper extends BaseMapper<PictureData> {

    public List<PictureData> getPicture(@Param("datasetId") int datasetId);

    public boolean uploadPicture(@Param("url") String url,  @Param("datasetId") int datasetId);

    public boolean updatePictureResult( @Param("pictureId") int picturetId,  @Param("pictureResult") String pictureResult);

    public int deleteDatasetPictures(@Param("datasetId") int datasetId);

    public PictureData getPictureByUrl(String pictureUrl);

}

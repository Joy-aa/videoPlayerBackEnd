package org.newhome.service;

import org.newhome.entity.PictureData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.info.PictureDataInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
public interface IPictureDataService extends IService<PictureData> {

    public boolean deletePictures(List<Integer> ids);

    public List<PictureDataInfo> getPictures(int datasetId);

    public boolean updatePictureResult(int pictureId, String pictureResult);

    public boolean uploadPictures(List<String> pictureSavePaths, int datasetId);

    public PictureDataInfo getPictureByUrl(String pictureUrl);
}

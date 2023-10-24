package org.newhome.service.impl;

import org.newhome.entity.PictureData;
import org.newhome.info.PictureDataInfo;
import org.newhome.mapper.CrackMapper;
import org.newhome.mapper.PictureDataMapper;
import org.newhome.service.IPictureDataService;
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
public class PictureDataServiceImpl extends ServiceImpl<PictureDataMapper, PictureData> implements IPictureDataService {

    @Resource
    PictureDataMapper pictureDataMapper;

    @Resource
    CrackMapper crackMapper;

    public boolean deletePictures(List<Integer> ids) {
        for (Integer i : ids) {
            pictureDataMapper.deleteById(i);
            crackMapper.deleteCrackByPictureId(i);
        }
        return true;
    }

    public List<PictureDataInfo> getPictures(int id) {
        List<PictureDataInfo> result = new ArrayList<>();
        List<PictureData> picture = pictureDataMapper.getPicture(id);
        for(PictureData pictureData: picture){
            result.add(pictureData.change());
        }
        return result;
    }

    public boolean updatePictureResult(int pictureId, String pictureResult){
        return pictureDataMapper.updatePictureResult(pictureId, pictureResult);
    }

    @Override
    public boolean uploadPictures(List<String> pictureSavePaths, int datasetId) {
        for (int i=0; i<pictureSavePaths.size(); i++){
            pictureDataMapper.uploadPicture(pictureSavePaths.get(i), datasetId);
        }
        return true;
    }

    @Override
    public PictureDataInfo getPictureByUrl(String pictureUrl) {
//        PictureDataInfo result = new PictureDataInfo();
        PictureData pictureData = pictureDataMapper.getPictureByUrl(pictureUrl);
        if(pictureData != null)
            return pictureData.change();
        else
            return null;
    }

}

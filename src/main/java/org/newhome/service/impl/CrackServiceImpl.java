package org.newhome.service.impl;

import org.newhome.entity.Crack;
import org.newhome.info.CrackInfo;
import org.newhome.mapper.CrackMapper;
import org.newhome.service.ICrackService;
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
public class CrackServiceImpl extends ServiceImpl<CrackMapper, Crack> implements ICrackService {

    @Resource
    CrackMapper crackMapper;

    @Override
    public boolean addCracks(List<CrackInfo> crackInfos) {
        for (int i = 0; i < crackInfos.size(); i++) {
            if (!crackMapper.addCrack(crackInfos.get(i).change()))
                return false;
        }
        return true;
    }

    @Override
    public boolean deleteCrack(int crackId) {
        return crackMapper.deleteCrack(crackId) == 1;
    }

    @Override
    public List<CrackInfo> getCrack(int pictureId) {
        List<CrackInfo> crackInfoList = new ArrayList<>();
        List<Crack> crackList = crackMapper.getCrack(pictureId);
        for(Crack crack: crackList){
            crackInfoList.add(crack.change());
        }
        return crackInfoList;
    }

    @Override
    public boolean deleteCrackByPictureId(int pictureId) {
        return crackMapper.getCrack(pictureId).size() == crackMapper.deleteCrackByPictureId(pictureId);
    }
}

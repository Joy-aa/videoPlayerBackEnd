package org.newhome.service;

import org.newhome.entity.Crack;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.info.CrackInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
public interface ICrackService extends IService<Crack> {
    public boolean addCracks(List<CrackInfo> crackInfos);

    public boolean deleteCrack(int crackId);

    public List<CrackInfo> getCrack(int pictureId);

    public boolean deleteCrackByPictureId(int pictureId);


}

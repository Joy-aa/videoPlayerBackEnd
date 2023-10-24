package org.newhome.mapper;

import org.newhome.entity.Crack;
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
public interface CrackMapper extends BaseMapper<Crack> {

    public boolean addCrack(Crack crack);

    public Integer deleteCrack(int crackId);

    public Integer deleteCrackByPictureId(int pictureId);

    public List<Crack> getCrack(int pictureId);

}

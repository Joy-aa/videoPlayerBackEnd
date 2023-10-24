package org.newhome.mapper;

import org.newhome.entity.Algorithm;
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
public interface AlgorithmMapper extends BaseMapper<Algorithm> {

    public Algorithm findAlgoByName(String algorithmName);
    public List<Algorithm> searchAlgorithm();

}

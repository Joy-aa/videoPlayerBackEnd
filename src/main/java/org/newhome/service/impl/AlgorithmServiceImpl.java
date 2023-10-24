package org.newhome.service.impl;

import org.newhome.entity.Algorithm;
import org.newhome.info.AlgorithmInfo;
import org.newhome.mapper.AlgorithmMapper;
import org.newhome.service.IAlgorithmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Service
public class AlgorithmServiceImpl extends ServiceImpl<AlgorithmMapper, Algorithm> implements IAlgorithmService {
    @Resource
    AlgorithmMapper algorithmMapper;

    @Override
    public AlgorithmInfo findAlgoByName(String algorithmName){
        Algorithm algorithm = algorithmMapper.findAlgoByName(algorithmName);
        if(algorithm == null)
            return null;
        else
            return algorithm.change();
    }

    @Override
    public List<AlgorithmInfo> searchAlgorithm(){
        List<Algorithm> temp= algorithmMapper.searchAlgorithm();
        List<AlgorithmInfo> result = new ArrayList<AlgorithmInfo>();
        for (Algorithm obj: temp){
            result.add(obj.change());
        }
        return result;
    }
}

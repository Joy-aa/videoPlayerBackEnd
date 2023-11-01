package org.newhome.service;

import org.newhome.entity.Star;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【star】的数据库操作Service
* @createDate 2023-10-27 15:58:16
*/
public interface StarService extends IService<Star> {

    Star addStar(Star star);

    int delete(int starId);

    int deleteAll(int userId);

    List<Star> getAll(int userId);

    Star getOne(int userId, int videoId);

}

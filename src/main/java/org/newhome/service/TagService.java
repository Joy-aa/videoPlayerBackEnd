package org.newhome.service;

import org.newhome.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.newhome.entity.User;

import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【tag】的数据库操作Service
* @createDate 2023-10-27 15:58:06
*/
public interface TagService extends IService<Tag> {
//    int addTag(Tag tag);
//
//    void updateTagName(String tagName,String newTagName);

    Tag findTagById(Integer tagId);

    List<Tag> getAllTag();

//    List<Tag> findTagByIdList(List<Integer> tagIdList);

//    Tag findTagByName(String tagName);
//
//    int deleteTag(String tagName);





}

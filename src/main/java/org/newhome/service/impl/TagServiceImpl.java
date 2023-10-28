package org.newhome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.newhome.entity.Tag;
import org.newhome.entity.User;
import org.newhome.mapper.UserMapper;
import org.newhome.service.TagService;
import org.newhome.mapper.TagMapper;
import org.newhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Yuxin Wang
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2023-10-27 15:58:06
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;


    @Override
    public Tag findTagById(Integer tagId){
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
        wrapper.eq(Tag::getTagId, tagId);
        Tag tag = tagMapper.selectOne(wrapper);
        return tag;

    }

    @Override
    public List<Tag> getAllTag(){
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
        List<Tag> allTag = tagMapper.selectList(wrapper);
        return allTag;
    }


//    @Override
//    public List<Tag> findTagByIdList(List<Integer> tagIdList){
////        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
//        List<Tag> result = new ArrayList<>();
//
//        for(int i=0;i<tagIdList.size();i++){
//
//            result.add(findTagById(tagIdList.get(i)));
//
//        }
//        return result;
//
//
//    }

//    @Override
//    public int addTag(Tag tag){
//        int num = tagMapper.insert(tag);
//        return num;
//
//    }
//    @Override
//    public void updateTagName(String tagName,String newTagName){
//        LambdaUpdateWrapper<Tag> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//
//        lambdaUpdateWrapper.eq(Tag::getTagName, tagName)
//                .set(Tag::getTagName, newTagName);
//        tagMapper.update(null, lambdaUpdateWrapper);
//    }



//    @Override
//    public Tag findTagByName(String tagName){
//        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
//        wrapper.eq(Tag::getTagName, tagName);
//        Tag tag = tagMapper.selectOne(wrapper);
//        return tag;
//
//    }
//
//    @Override
//    public int deleteTag(String tagName){
//        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>();
//        wrapper.eq(Tag::getTagName, tagName);
//        int num = tagMapper.delete(wrapper);
//        return num;
//
//    }


}








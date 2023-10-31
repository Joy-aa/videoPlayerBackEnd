package org.newhome.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.service.TagService;
import org.newhome.util.ResultBean;
import org.newhome.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author joy_aa
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/tag")
@Api(tags = "Tag管理")
@ApiSupport(author = "cxy")
public class TagController {
    @Autowired
    private TagService tagService;

//    @CrossOrigin
//    @ApiOperation("新增Tag")
//    @PostMapping(value = "/addtag")
//    public ResultBean<Integer> addTag(@RequestBody @Validated Tag tag){
//        ResultBean<Integer> result = new ResultBean<>();
//        int num = tagService.addTag(tag);
//        if(num > 0) {
//            result.setMsg("新增Tag成功");
//            result.setCode(ResultBean.SUCCESS);
//            result.setData(num);
//        }
//        else {
//            result.setMsg("新增Tag失败");
//            result.setCode(ResultBean.FAIL);
//            result.setData(num);
//        }
//        return result;
//    }


    @CrossOrigin
    @ApiOperation("按照TagId查找")
    @GetMapping(value = "findtagbyid")
    public ResultBean<Tag> findTagById(int  tagId){
        ResultBean<Tag> result = new ResultBean<>();
        Tag tag = tagService.findTagById(tagId);
        if(tag != null){
            result.setMsg("Tag查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(tag);
        }
        else{
            result.setMsg("查询不到对应Tag");
            result.setCode(ResultBean.FAIL);
            result.setData(tag);
        }
        return result;
    }
    @CrossOrigin
    @ApiOperation("按照TagName查找")
    @GetMapping(value = "findtagbyname")
    public ResultBean<Tag> findTagByName(String  tagname){
        ResultBean<Tag> result = new ResultBean<>();
        Tag tag = tagService.findTagByName(tagname);
        if(tag != null){
            result.setMsg("Tag查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(tag);
        }
        else{
            result.setMsg("查询不到对应Tag");
            result.setCode(ResultBean.FAIL);
            result.setData(tag);
        }
        return result;
    }
    @CrossOrigin
    @ApiOperation("查找所有Tag")
    @GetMapping(value = "getalltag")
    public ResultBean<List<Tag>> getAllTag(){
        ResultBean<List<Tag>> result = new ResultBean<>();
        List<Tag> tag = tagService.getAllTag();
        if(tag != null){
            result.setMsg("Tag查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(tag);
        }
        else{
            result.setMsg("查询不到对应Tag");
            result.setCode(ResultBean.FAIL);
            result.setData(tag);
        }
        return result;
    }
//
//    @CrossOrigin
//    @ApiOperation("根据Tag列表查找所有Tag")
//    @GetMapping(value = "findtagbyidlist")
//    public ResultBean<List<Tag>> findTagByIdList(List<Integer> tagIdList){
//        ResultBean<List<Tag>> result = new ResultBean<>();
//        List<Tag> tag = tagService.findTagByIdList(tagIdList);
//        if(tag != null){
//            result.setMsg("Tag查询成功");
//            result.setCode(ResultBean.SUCCESS);
//            result.setData(tag);
//        }
//        else{
//            result.setMsg("查询不到对应Tag");
//            result.setCode(ResultBean.FAIL);
//            result.setData(tag);
//        }
//        return result;
//    }
//
}



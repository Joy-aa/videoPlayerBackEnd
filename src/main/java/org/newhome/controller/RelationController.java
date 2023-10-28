package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.Relation;
import org.newhome.entity.User;
import org.newhome.req.RelationReq;
import org.newhome.service.RelationService;
import org.newhome.service.UserService;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "用户关系")
@RestController
@RequestMapping("/relation")
public class RelationController {
    @Autowired
    RelationService relationService;
    @Autowired
    UserService userService;


    //新增关系
    @CrossOrigin
    @ApiOperation("新增关系")
    @PostMapping("addRelation")
    @FilterAnnotation(url = "/relation/addRelation", type = FilterType.login)
    public ResultBean<Relation> addRelation(RelationReq relationReq) {
        ResultBean<Relation> result = new ResultBean<>();
        User user1 = userService.findById(relationReq.getUserid1());
        User user2 = userService.findById(relationReq.getUserid2());
        int kind = relationReq.getKind();
        if (user1 == null) {
            result.setMsg("用户不存在，请重新登录");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        else if (user2 == null) {
            if (kind == 0)
                result.setMsg("关注的用户不存在");
            else if (kind == 1)
                result.setMsg("拉黑的用户不存在");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if (kind != 0 && kind != 1) {
            result.setMsg("操作码错误");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            // 如果拉黑了则不能关注，只能取消拉黑然后关注
            if (kind == 0) {
                List<Relation> hateList = relationService.findRelations(relationReq.getUserid1(),
                        relationReq.getUserid2(), 1);
                if (hateList.size() > 0) {
                    result.setMsg("用户在黑名单中，关注失败");
                    result.setCode(ResultBean.FAIL);
                    result.setData(null);
                }
                else {
                    Relation relation = relationService.addRelation(user1, user2, kind);
                    if (relation != null) {
                        result.setMsg("关注成功");
                        result.setCode(ResultBean.SUCCESS);
                        result.setData(relation);
                    }
                }
            }
            // 如果关注了之后想拉黑，需要把关注关系修改为拉黑关系
            else {
                List<Relation> followList = relationService.findRelations(relationReq.getUserid1(),
                        relationReq.getUserid2(), 0);
                if (followList.size() > 0) {
                    int res = relationService.modifyRelation(user1, user2, kind);
                    if (res != 0){
                        result.setMsg("拉黑成功");
                        result.setCode(ResultBean.SUCCESS);
                        Relation relation = followList.get(0);
                        relation.setKind(kind);
                        result.setData(relation);
                    }
                }
                else {
                    Relation relation = relationService.addRelation(user1, user2, kind);
                    if (relation != null){
                        result.setMsg("拉黑成功");
                        result.setCode(ResultBean.SUCCESS);
                        result.setData(relation);
                    }
                }
            }
        }
        return result;
    }

    //解除关系
    @CrossOrigin
    @ApiOperation("解除关系")
    @PostMapping("deleteRelation")
    @FilterAnnotation(url = "/relation/deleteRelation", type = FilterType.login)
    public ResultBean<Relation> deleteRelation(RelationReq relationReq) {
        ResultBean<Relation> result = new ResultBean<>();
        User user1 = userService.findById(relationReq.getUserid1());
        User user2 = userService.findById(relationReq.getUserid2());
        int kind = relationReq.getKind();
        if (user1 == null) {
            result.setMsg("用户不存在，请重新登录");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        else if (user2 == null) {
            if (kind == 0)
                result.setMsg("取消关注的用户不存在");
            else if (kind == 1)
                result.setMsg("取消拉黑的用户不存在");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if (kind != 0 && kind != 1) {
            result.setMsg("操作码错误");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            int res = relationService.deleteRelation(user1, user2, kind);
            if(res != -1){
                if (kind == 0)
                    result.setMsg("取消关注成功");
                else
                    result.setMsg("取消拉黑成功");
                result.setData(null);
            }
            else{
                if (kind == 0)
                    result.setMsg("取消关注失败");
                else
                    result.setMsg("取消拉黑失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        return result;
    }

    //查询关系
    @CrossOrigin
    @ApiOperation("查询关系，关注/粉丝/黑名单")
    @GetMapping("findRelations")
    @FilterAnnotation(url="/relation/findRelations",type = FilterType.login)
    public ResultBean<List<Relation>> findRelations(RelationReq relationReq) {
        ResultBean<List<Relation>> result = new ResultBean<>();
        if (relationReq.getUserid1() == null && relationReq.getUserid2() == null) {
            result.setMsg("用户不存在，请重新登录");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        else if (relationReq.getKind() == null || relationReq.getKind() != 0 && relationReq.getKind() != 1) {
            result.setMsg("操作码错误");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if (relationReq.getUserid2() != null && relationReq.getKind() == 1) {
            result.setMsg("无法查询谁拉黑了自己");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            List<Relation> relationList = relationService.findRelations(relationReq.getUserid1(), relationReq.getUserid2(), relationReq.getKind());
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(relationList);
        }
        return result;
    }

}

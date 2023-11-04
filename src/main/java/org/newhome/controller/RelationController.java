package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.Relation;
import org.newhome.entity.User;
import org.newhome.req.RelationReq;
import org.newhome.res.RelationRes;
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
    public ResultBean<RelationRes> addRelation(RelationReq relationReq) {
        ResultBean<RelationRes> result = new ResultBean<>();
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
            //如果两个人之间已经存在任何关系，则不能插入新关系，需要先解除
            Relation relation = relationService.findRelation(relationReq.getUserid1(), relationReq.getUserid2());
            RelationRes relationRes = new RelationRes();
            if(relation == null) {
                relationRes.setRelation(relationService.addRelation(user1, user2, kind));
                if(relationRes.getRelation() == null) {
                    result.setMsg("插入关系操作失败");
                    result.setCode(ResultBean.FAIL);
                    result.setData(null);
                }
                else{
                    result.setData(relationRes);
                    result.setMsg("关注/拉黑成功");
                }
            }
            else{
                relationRes.setRelation(relation);
                result.setMsg("两个用户间已经存在关系");
                result.setData(relationRes);
            }
//            // 如果拉黑了则不能关注，只能取消拉黑然后关注
//            if (kind == 0) {
//                Relation hate = relationService.findRelation(relationReq.getUserid1(),
//                        relationReq.getUserid2());
//                if (hate != null && hate.getKind() == 1) {
//                    result.setMsg("用户在黑名单中，关注失败");
//                    result.setCode(ResultBean.FAIL);
//                    result.setData(null);
//                }
//                else {
//                    Relation relation = relationService.addRelation(user1, user2, kind);
//                    if (relation != null) {
//                        result.setMsg("关注成功");
//                        result.setCode(ResultBean.SUCCESS);
//                        result.setData(relation);
//                    }
//                }
//            }
//            // 如果关注了之后想拉黑，需要把关注关系修改为拉黑关系
//            else {
//                Relation follow = relationService.findRelation(relationReq.getUserid1(),
//                        relationReq.getUserid2());
//                if (follow != null && follow.getKind() == 0) {
//                    int res = relationService.modifyRelation(user1, user2, kind);
//                    if (res != 0){
//                        result.setMsg("拉黑成功");
//                        result.setCode(ResultBean.SUCCESS);
//                        follow.setKind(kind);
//                        result.setData(follow);
//                    }
//                }
//                else {
//                    Relation relation = relationService.addRelation(user1, user2, kind);
//                    if (relation != null){
//                        result.setMsg("拉黑成功");
//                        result.setCode(ResultBean.SUCCESS);
//                        result.setData(relation);
//                    }
//                }
//            }
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
                result.setCode(ResultBean.SUCCESS);
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
    @ApiOperation("查询关系，我关注/拉黑了谁")
    @GetMapping("findFollows")
    @FilterAnnotation(url="/relation/findFollows",type = FilterType.login)
    public ResultBean<List<Relation>> findFollows(RelationReq relationReq) {
        ResultBean<List<Relation>> result = new ResultBean<>();
        if (relationReq.getUserid1() == null) {
            result.setMsg("用户不存在，请重新登录");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        else if (relationReq.getKind() == null || relationReq.getKind() != 0 && relationReq.getKind() != 1) {
            result.setMsg("操作码错误");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            List<Relation> relationList = relationService.findFollows(relationReq.getUserid1(), relationReq.getKind());
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(relationList);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("查询关系，谁关注/拉黑了我")
    @GetMapping("findFans")
    @FilterAnnotation(url="/relation/findFans",type = FilterType.login)
    public ResultBean<List<Relation>> findFans(RelationReq relationReq) {
        ResultBean<List<Relation>> result = new ResultBean<>();
        if (relationReq.getUserid2() == null) {
            result.setMsg("用户不存在，请重新登录");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        else if (relationReq.getKind() == null || relationReq.getKind() != 0 && relationReq.getKind() != 1) {
            result.setMsg("操作码错误");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            List<Relation> relationList = relationService.findFollows(relationReq.getUserid2(), relationReq.getKind());
            result.setMsg("查询成功");
            result.setCode(ResultBean.SUCCESS);
            result.setData(relationList);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("查询用户1和用户2是否存在关系")
    @GetMapping("findRelation")
    @FilterAnnotation(url="/relation/findRelation",type = FilterType.login)
    public ResultBean<RelationRes> findRelation(RelationReq relationReq) {
        ResultBean<RelationRes> result = new ResultBean<>();
        User user1 = userService.findById(relationReq.getUserid1());
        User user2 =  userService.findById(relationReq.getUserid2());
        if(user1 == null || user2 == null) {
            result.setMsg("用户不存在，请重新登录");
            result.setCode(ResultBean.NO_LOGIN);
            result.setData(null);
            return result;
        }
        RelationRes relationRes = new RelationRes();
        relationRes.setRelation(relationService.findRelation(relationReq.getUserid1(), relationReq.getUserid2()));
        if(relationRes.getRelation() == null) {
            result.setMsg("不存在关系");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        else {
            result.setMsg("查询成功");
            result.setData(relationRes);
        }

        return result;
    }

}

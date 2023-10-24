package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.info.CrackInfo;
import org.newhome.req.DeleteCrackReq;
import org.newhome.req.GetCrackReq;
import org.newhome.res.DeleteCrackRes;
import org.newhome.res.GetCrackRes;
import org.newhome.service.ICrackService;
import org.newhome.util.ResultBean;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Api(tags = "裂缝")
@RestController
@RequestMapping("/crack")
public class CrackController {

    @Autowired
    ICrackService iCrackService;


    @CrossOrigin
    @ApiOperation("删除裂缝")
    @PostMapping("deleteCrack")
    @FilterAnnotation(url = "/crack/deleteCrack", type = FilterType.login)
    public ResultBean<DeleteCrackRes> deleteCrack(@RequestBody DeleteCrackReq req){
        ResultBean<DeleteCrackRes> res = new ResultBean<>();
        DeleteCrackRes rs = new DeleteCrackRes();
        rs.setSucceed(iCrackService.deleteCrack(req.getCrackId()));
        if (rs.isSucceed()){
            res.setMsg("删除成功");
        } else {
            res.setMsg("删除时发生未知错误");
            res.setCode(ResultBean.FAIL);
        }
        res.setData(rs);
        return res;
    }


    @CrossOrigin
    @ApiOperation("获取裂缝信息")
    @PostMapping("getCrack")
    @FilterAnnotation(url = "/crack/getCrack", type = FilterType.login)
    public ResultBean<GetCrackRes> getCrack(@RequestBody GetCrackReq req){
        ResultBean<GetCrackRes> res = new ResultBean<>();
        GetCrackRes rs = new GetCrackRes();
        List<CrackInfo> crackInfoList = iCrackService.getCrack(req.getPictureId());
        rs.setCrackInfos(crackInfoList);
        res.setData(rs);
        res.setMsg("查询成功");
        return res;
    }

}

package org.newhome.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.info.AlgorithmInfo;
import org.newhome.info.DatasetInfo;
import org.newhome.info.ModelInfo;
import org.newhome.info.UserInfo;
import org.newhome.req.*;
import org.newhome.res.AddModelRes;
import org.newhome.res.FindModelRes;
import org.newhome.res.ModelRes;
import org.newhome.service.IAlgorithmService;
import org.newhome.service.IDatasetService;
import org.newhome.service.IModelService;
import org.newhome.util.FileUtil;
import org.newhome.util.ResultBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */

@Api(tags = "模型")
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    IModelService iModelService;

    @Autowired
    IAlgorithmService iAlgorithmService;

    @Autowired
    IDatasetService iDatasetService;

    @CrossOrigin
    @ApiOperation("添加模型记录")
    @PostMapping("add")
    @FilterAnnotation(url = "/model/add", type = FilterType.anno)
    public ResultBean<AddModelRes> addModel (@RequestBody AddModelReq modelReq){
        ResultBean<AddModelRes> result = new ResultBean<>();
        if(!StringUtils.hasText(modelReq.getModelName())){
            result.setCode(ResultBean.FAIL);
            result.setMsg("输入模型名称不合法！");
            result.setData(null);
            return result;
        }
        AddModelRes addModelRes = new AddModelRes();
        addModelRes.setModelInfo(iModelService.findModelByName(modelReq.getUsername(),modelReq.getModelName()));
        if(addModelRes.getModelInfo()==null){
            ModelInfo modelInfo = new ModelInfo();
            modelInfo.setModelName(modelReq.getModelName());
            modelInfo.setUsername(modelReq.getUsername());
            AlgorithmInfo algorithmInfo = iAlgorithmService.findAlgoByName(modelReq.getAlgorithm());
            if(algorithmInfo == null){
                result.setCode(ResultBean.FAIL);
                result.setMsg("算法不存在");
                result.setData(null);
                return result;
            }
            modelInfo.setAlgoId(algorithmInfo.getAlgoId());
            modelInfo.setUsername(modelReq.getUsername());
            DatasetInfo datasetInfo = iDatasetService.findDatasetByUserAndName(modelReq.getUsername(), modelReq.getDataset());
            if(datasetInfo == null){
                result.setCode(ResultBean.FAIL);
                result.setMsg("数据集不存在");
                result.setData(null);
                return result;
            }
            modelInfo.setDatasetId(datasetInfo.getDatasetId());
            //modelpath应该是用户传入的参数，callAlgotithm之后保存文件，这里暂时不处理
            int num = iModelService.addModel(modelInfo);
            addModelRes.setModelInfo(modelInfo);
            if(num > 0){
                result.setMsg("历史记录插入成功！");
                result.setData(addModelRes);
            }
            else{
                result.setMsg("历史记录插入失败");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        else{
            result.setData(null);
            result.setMsg("同一用户下模型名称重复，请重新命名！");
            result.setCode(ResultBean.FAIL);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名查找所有模型记录")
    @PostMapping("findAll")
    @FilterAnnotation(url = "/model/findAll", type = FilterType.login)
    public ResultBean<FindModelRes> findModelByUser(@RequestBody FindModelsByUserReq req, HttpSession httpSession, @CookieValue("userTicket")String ticket) {
        ResultBean<FindModelRes> result = new ResultBean<>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
        if(userInfo == null || !StringUtils.hasText(ticket)) {
            result.setMsg("用户未登录，请跳转login页面");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        if(userInfo.getAuthority() > 0 && !req.getUsername().equals(userInfo.getUsername())){//非管理员无权限查看别的用户的历史记录
            result.setMsg("无权限查看该用户历史记录！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        FindModelRes models = new FindModelRes();
        models.setModelInfos(iModelService.findModelsByUser(req.getUsername()));
        if(models.getModelInfos() != null) {
            result.setMsg("查询成功！共"+models.getModelInfos().size()+"条记录");
            result.setData(models);
        }
        else {
            result.setMsg("查询失败！共0条记录");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名和算法名臣查找所有模型记录")
    @PostMapping("findByaAlgo")
    @FilterAnnotation(url = "/model/findByAlgo", type = FilterType.login)
    public ResultBean<FindModelRes> findModelByUserAndAlgo(@RequestBody FindModelByUserAndAlgoReq req, HttpSession httpSession, @CookieValue("userTicket")String ticket){
        ResultBean<FindModelRes> result = new ResultBean<>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
        if(userInfo == null || !StringUtils.hasText(ticket)) {
            result.setMsg("用户未登录，请跳转login页面");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        if(userInfo.getAuthority() > 0 && !req.getUsername().equals(userInfo.getUsername())){//非管理员无权限查看别的用户的历史记录
            result.setMsg("无权限查看该用户历史记录！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        FindModelRes models = new FindModelRes();
        AlgorithmInfo algorithmInfo = iAlgorithmService.findAlgoByName(req.getAlgorithm());
        if(algorithmInfo == null){
            result.setCode(ResultBean.FAIL);
            result.setMsg("算法不存在");
            result.setData(null);
            return result;
        }
        models.setModelInfos(iModelService.findModelsByUserAndAlgo(req.getUsername(), algorithmInfo.getAlgoId()));
        if(models.getModelInfos() != null) {
            result.setMsg("查询成功！共"+models.getModelInfos().size()+"条记录");
            result.setData(models);
        }
        else {
            result.setMsg("查询失败！共0条记录");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名和数据集查找所有模型记录")
    @PostMapping("findByDataset")
    @FilterAnnotation(url = "/model/findByDataset", type = FilterType.login)
    public ResultBean<FindModelRes> findModelByUserAndDataset(@RequestBody FindModelByUserAndDatasetReq req, HttpSession httpSession, @CookieValue("userTicket")String ticket){
        ResultBean<FindModelRes> result = new ResultBean<>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
        if(userInfo == null || !StringUtils.hasText(ticket)) {
            result.setMsg("用户未登录，请跳转login页面");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        if(userInfo.getAuthority() > 0 && !req.getUsername().equals(userInfo.getUsername())){//非管理员无权限查看别的用户的历史记录
            result.setMsg("无权限查看该用户历史记录！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        DatasetInfo datasetInfo = iDatasetService.findDatasetByUserAndName(req.getUsername(), req.getDataset());
        if(datasetInfo == null){
            result.setCode(ResultBean.FAIL);
            result.setMsg("数据集不存在");
            result.setData(null);
            return result;
        }
        FindModelRes models = new FindModelRes();
        models.setModelInfos(iModelService.findModelsByUserAndDataset(req.getUsername(), datasetInfo.getDatasetId()));
        if(models.getModelInfos() != null) {
            result.setMsg("查询成功！共"+models.getModelInfos().size()+"条记录");
            result.setData(models);
        }
        else {
            result.setMsg("查询失败！共0条记录");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名，数据集和算法名称查找所有模型记录")
    @PostMapping("findByALgoAndDataset")
    @FilterAnnotation(url = "/model/findByAlgoAndDataset", type = FilterType.login)
    public ResultBean<FindModelRes> findModelByUserAndAlgoAndDataset(@RequestBody FindModelByUserAndAlgoAndDataset req, HttpSession httpSession, @CookieValue("userTicket")String ticket){
        ResultBean<FindModelRes> result = new ResultBean<>();
        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
        if(userInfo == null || !StringUtils.hasText(ticket)) {
            result.setMsg("用户未登录，请跳转login页面");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        if(userInfo.getAuthority() > 0 && !req.getUsername().equals(userInfo.getUsername())){//非管理员无权限查看别的用户的历史记录
            result.setMsg("无权限查看该用户历史记录！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        AlgorithmInfo algorithmInfo = iAlgorithmService.findAlgoByName(req.getAlgorithm());
        if(algorithmInfo == null){
            result.setCode(ResultBean.FAIL);
            result.setMsg("算法不存在");
            result.setData(null);
            return result;
        }
        DatasetInfo datasetInfo = iDatasetService.findDatasetByUserAndName(req.getUsername(), req.getDataset());
        if(datasetInfo == null){
            result.setCode(ResultBean.FAIL);
            result.setMsg("数据集不存在");
            result.setData(null);
            return result;
        }
        FindModelRes models = new FindModelRes();
        models.setModelInfos(iModelService.findModelsByUserAndAlgoAndDataset(req.getUsername(), algorithmInfo.getAlgoId(), datasetInfo.getDatasetId()));
        if(models.getModelInfos() != null) {
            result.setMsg("查询成功！共"+models.getModelInfos().size()+"条记录");
            result.setData(models);
        }
        else {
            result.setMsg("查询失败！共0条记录");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名和模型名称删除模型记录")
    @PostMapping("delete")
    @FilterAnnotation(url = "/model/delete", type = FilterType.login)
    public ResultBean<AddModelRes> deleteModel(@RequestBody DeleteModelReq req){
        ResultBean<AddModelRes> result = new ResultBean<>();
        AddModelRes addModelRes = new AddModelRes();
        addModelRes.setModelInfo(iModelService.findModelByName(req.getUsername(),req.getModelName()));
        if(addModelRes.getModelInfo() == null){
            result.setCode(ResultBean.FAIL);
            result.setMsg("该条历史记录不存在");
            result.setData(null);
        }
        else{
            boolean flag = iModelService.deleteModel(req.getUsername(), req.getModelName());
            if(flag){
                result.setMsg("删除历史记录成功");
                result.setData(addModelRes);
            }
            else{
                result.setCode(ResultBean.FAIL);
                result.setMsg("删除历史记录失败");
                result.setData(null);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名重命名模型记录")
    @PostMapping("update")
    @FilterAnnotation(url = "/model/update", type = FilterType.login)
    public ResultBean<AddModelRes> updateModel(@RequestBody UpdateModelReq req){
        ResultBean<AddModelRes> result = new ResultBean<>();
        AddModelRes res = new AddModelRes();
        res.setModelInfo(iModelService.findModelByName(req.getUsername(), req.getModelName()));
        if(res.getModelInfo() == null) {
            result.setMsg("历史记录不存在");
            result.setData(null);
            result.setCode(ResultBean.FAIL);
        }
        else {
            boolean flag = iModelService.updateModel(res.getModelInfo().getModelId(), req.getNewName());
            if(flag){
                res.getModelInfo().setModelName(req.getNewName());
                result.setData(res);
                result.setMsg("修改模型名称成功");
            }
            else{
                result.setMsg("修改失败");
                result.setData(null);
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据用户名下载模型记录")
    @PostMapping("/downloadModel")
    @FilterAnnotation(url = "/model/downloadModel", type = FilterType.login)
    public ResultBean<ModelRes> download(HttpServletRequest request, HttpServletResponse response, DownloadModelReq req) {
        ResultBean<ModelRes> result = new ResultBean<>();
        ModelRes modelRes =  new ModelRes();
        modelRes.setModelInfo(iModelService.findModelByName(req.getUsername(), req.getModel()));
        if(modelRes.getModelInfo() == null) {
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            result.setMsg("该条历史记录不存在！");
        }
        else {
             //文件本地位置
            String filePath = modelRes.getModelInfo().getModelPath();
            int pos = filePath.lastIndexOf(".");
            if(pos == -1){
                result.setCode(ResultBean.FAIL);
                result.setData(null);
                result.setMsg("模型存储地址错误！");
            }
            else{
//        String filePath ="F:\\java\\Dam-Backend\\src\\main\\resources\\static\\model\\test.txt";
        // 文件名称
                String fileName = filePath.substring(pos);
                File file = new File(filePath);
                FileUtil.downloadFile(file, request, response, fileName);
                result.setMsg("下载成功");
                result.setData(modelRes);
            }
        }
        return result;
    }
}

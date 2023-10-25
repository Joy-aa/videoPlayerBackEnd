//package org.newhome.controller;
//
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import org.newhome.annotation.FilterAnnotation;
//import org.newhome.config.Constant;
//import org.newhome.config.FilterType;
//import org.newhome.info.DatasetInfo;
//import org.newhome.info.PictureDataInfo;
//import org.newhome.info.UserInfo;
//import org.newhome.req.*;
//import org.newhome.res.*;
//import org.newhome.service.IDatasetService;
//import org.newhome.service.IPictureDataService;
//import org.newhome.service.IUserService;
//import org.newhome.util.ResultBean;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// * 前端控制器
// * </p>
// *
// * @author panyan
// * @since 2022-08-05
// */
//@Slf4j
//@Api(tags = "数据集")
//@RestController
//@RequestMapping("/dataset")
//public class DatasetController {
//    @Autowired
//    IDatasetService iDatasetService;
//
//    @Autowired
//    IPictureDataService iPictureDataService;
//
//    @Autowired
//    IUserService iUserService;
//
//    @ApiOperation("创建数据集")
//    @CrossOrigin
//    @PostMapping("/createDataset")
//    @FilterAnnotation(url = "/dataset/createDataset", type = FilterType.login)
//    public ResultBean<CreateDatasetRes> createDataset(@RequestBody CreateDatasetReq req) {
//        ResultBean<CreateDatasetRes> result = new ResultBean<>();
//        CreateDatasetRes res = new CreateDatasetRes();
//        UserInfo userInfo = iUserService.findByUsername(req.getUsername());
//        if (userInfo == null) {
//            result.setMsg("用户不存在");
//            result.setCode(ResultBean.FAIL);
//            return result;
//        }
//        res.setSucceed(iDatasetService.createDataset(req.getUsername(), req.getDatasetName(), req.isPublic()));
//        res.setDatasetInfo(iDatasetService.getNewDataset());
//        if (res.isSucceed()) {
//            result.setMsg("创建成功");
//            result.setData(res);
//        } else {
//            result.setMsg("创建失败");
//            result.setCode(ResultBean.FAIL);
//        }
//        return result;
//    }
//
//    @ApiOperation("查询用户数据集")
//    @CrossOrigin
//    @PostMapping("/getUserDatasets")
//    @FilterAnnotation(url = "/dataset/createDataset", type = FilterType.login)
//    public ResultBean<List<GetUserDatasetsRes>> getUserDatasets(@RequestBody GetUserDatasetsReq req) {
//        ResultBean<List<GetUserDatasetsRes>> result = new ResultBean<>();
//        List<GetUserDatasetsRes> res = new ArrayList<>();
//        List<DatasetInfo> datasetInfos = iDatasetService.getUserDatasets(req.getUsername());
//        for (DatasetInfo datasetInfo: datasetInfos){
//            int datasetId = datasetInfo.getDatasetId();
//            List<PictureDataInfo> pictureDataInfos = iPictureDataService.getPictures(datasetId);
//            if (pictureDataInfos.size()>=10){
//                pictureDataInfos = pictureDataInfos.subList(0, 9);
//            }
//            GetUserDatasetsRes res1 = new GetUserDatasetsRes();
//            res1.setDatasetInfo(datasetInfo);
//            res1.setPictureDataInfos(pictureDataInfos);
//            res.add(res1);
//        }
//        result.setData(res);
//        return result;
//    }
//
//    @ApiOperation("查询公共数据集")
//    @CrossOrigin
//    @PostMapping("/getPublicDatasets")
//    @FilterAnnotation(url = "/dataset/getPublicDatasets", type = FilterType.login)
//    public ResultBean<List<GetPublicDatasetsRes>> getPublicDatasets() {
//        ResultBean<List<GetPublicDatasetsRes>> result = new ResultBean<>();
//        List<GetPublicDatasetsRes> res = new ArrayList<>();
//        List<DatasetInfo> datasetInfos = iDatasetService.getPublicDatasets();
//        for (DatasetInfo datasetInfo: datasetInfos){
//            int datasetId = datasetInfo.getDatasetId();
//            List<PictureDataInfo> pictureDataInfos = iPictureDataService.getPictures(datasetId);
//            if (pictureDataInfos.size()>=10){
//                pictureDataInfos = pictureDataInfos.subList(0, 9);
//            }
//            GetPublicDatasetsRes res1 = new GetPublicDatasetsRes();
//            res1.setDatasetInfo(datasetInfo);
//            res1.setPictureDataInfos(pictureDataInfos);
//            res.add(res1);
//        }
//        result.setData(res);
//        return result;
//    }
//
//    @ApiOperation("删除数据集")
//    @CrossOrigin
//    @PostMapping("/deleteDataset")
//    @FilterAnnotation(url = "/dataset/deleteDataset", type = FilterType.login)
//    public ResultBean<DeleteDatasetRes> deleteDataset(@RequestBody DeleteDatasetReq req) {
//        ResultBean<DeleteDatasetRes> result = new ResultBean<>();
//        DeleteDatasetRes res = new DeleteDatasetRes();
//        DatasetInfo datasetInfo = iDatasetService.getDatasetInfo(req.getDatasetId());
//        List<PictureDataInfo> pictureDataInfos = iPictureDataService.getPictures(req.getDatasetId());
//        res.setSucceed(iDatasetService.deleteDataset(req.getDatasetId()));
//        result.setData(res);
//        if (res.isSucceed()) {
//            for(int i = 0; i < pictureDataInfos.size(); i++) {
//                String url = pictureDataInfos.get(i).getPictureUrl();
//                int beginIndex = url.indexOf("/img/") + 5;
//                String filePath = Constant.ORI_IMAGE_PATH + url.substring(beginIndex);
//                log.info("filePath:" + filePath);
//                File file = new File(filePath);
//                // 路径为文件且不为空则进行删除
//                if (file.isFile() && file.exists()) {
//                    file.delete();
//                }
//            }
//            result.setMsg("删除成功");
//        } else {
//            result.setMsg("删除失败，请检查数据集ID是否正确");
//            result.setCode(ResultBean.FAIL);
//        }
//        return result;
//    }
//
//    @ApiOperation("获取数据集信息")
//    @CrossOrigin
//    @PostMapping("/getDatasetInfo")
//    @FilterAnnotation(url = "/dataset/getDatasetInfo", type = FilterType.login)
//    public ResultBean<GetDatasetInfoRes> getDatasetInfo(@RequestBody GetDatasetInfoReq req) {
//        ResultBean<GetDatasetInfoRes> result = new ResultBean<>();
//        GetDatasetInfoRes res = new GetDatasetInfoRes();
//        res.setDatasetInfo(iDatasetService.getDatasetInfo(req.getDatasetId()));
//        if (res.getDatasetInfo() != null) {
//            res.setPictureDataInfos(iPictureDataService.getPictures(req.getDatasetId()));
//            result.setMsg("查询成功");
//            result.setData(res);
//        } else {
//            result.setMsg("未找到相应数据集信息");
//            result.setCode(ResultBean.FAIL);
//        }
//        return result;
//    }
//
//    @ApiOperation("重命名数据集")
//    @CrossOrigin
//    @PostMapping("/renameDataset")
//    @FilterAnnotation(url = "/dataset/renameDataset", type = FilterType.login)
//    public ResultBean<RenameDatasetRes> renameDataset(@RequestBody RenameDatasetReq req) {
//        ResultBean<RenameDatasetRes> result = new ResultBean<>();
//        RenameDatasetRes res = new RenameDatasetRes();
//        res.setSucceed(iDatasetService.renameDataset(req.getDatasetId(), req.getNewName()));
//        if (res.isSucceed()) {
//            result.setData(res);
//            result.setMsg("修改成功");
//        } else {
//            result.setMsg("修改失败");
//            result.setCode(ResultBean.FAIL);
//        }
//        return result;
//    }
//
//    @ApiOperation("根据名称查询数据集")
//    @CrossOrigin
//    @PostMapping("/findDatasetByName")
//    @FilterAnnotation(url = "/dataset/findDatasetByName", type = FilterType.login)
//    public ResultBean<List<FindDatasetByUserAndNameRes>> findDataset(@RequestBody FindDatasetByUserAndNameReq req) {
//        ResultBean<List<FindDatasetByUserAndNameRes>> result = new ResultBean<>();
//        List<FindDatasetByUserAndNameRes> tmpresult = new ArrayList<>();
//        if (!StringUtils.hasText(req.getDatasetName())) {
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("输入数据集名称不合法！");
//            result.setData(null);
//            return result;
//        }
//        FindDatasetByUserAndNameRes res = new FindDatasetByUserAndNameRes();
//        res.setDatasetInfo(iDatasetService.findDatasetByUserAndName(req.getUsername(), req.getDatasetName()));
//        int datasetId = res.getDatasetInfo().getDatasetId();
//        List<PictureDataInfo> pictureDataInfos = iPictureDataService.getPictures(datasetId);
//        if (pictureDataInfos.size()>=10){
//            pictureDataInfos = pictureDataInfos.subList(0, 9);
//        }
//        res.setPictureDataInfos(pictureDataInfos);
//        if (res.getDatasetInfo() == null) {
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("不存在该数据集");
//            result.setData(null);
//        } else {
//            result.setMsg("查找成功，数据集名称为：" + res.getDatasetInfo().getDatasetName());
//            tmpresult.add(res);
//            result.setData(tmpresult);
//        }
//        return result;
//    }
//    @ApiOperation("根据名称模糊查询数据集")
//    @CrossOrigin
//    @PostMapping("/blurredFindDatasetByName")
//    @FilterAnnotation(url = "/dataset/blurredFindDatasetByName", type = FilterType.login)
//    public ResultBean<List<FindDatasetByUserAndNameRes>> blurredFindDataset(@RequestBody FindDatasetByUserAndNameReq req) {
//        ResultBean<List<FindDatasetByUserAndNameRes>> result = new ResultBean<>();
//        List<FindDatasetByUserAndNameRes> tmpresult = new ArrayList<>();
//        if (!StringUtils.hasText(req.getDatasetName())) {
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("输入数据集名称不合法！");
//            result.setData(null);
//            return result;
//        }
//        List<DatasetInfo> datasetInfos = iDatasetService.blurredFindDatasetByUserAndName(req.getUsername(), req.getDatasetName());
//        for(DatasetInfo d: datasetInfos){
//            FindDatasetByUserAndNameRes res = new FindDatasetByUserAndNameRes();
//            res.setDatasetInfo(d);
//            int datasetId = d.getDatasetId();
//            List<PictureDataInfo> pictureDataInfos = iPictureDataService.getPictures(datasetId);
//            if (pictureDataInfos.size()>=10){
//                pictureDataInfos = pictureDataInfos.subList(0, 9);
//            }
//            res.setPictureDataInfos(pictureDataInfos);
//            tmpresult.add(res);
//        }
//        if (datasetInfos.isEmpty()) {
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("不存在该数据集");
//            result.setData(null);
//        } else {
//            result.setMsg("查找成功");
//            result.setData(tmpresult);
//        }
//        return result;
//    }
//}

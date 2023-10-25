//package org.newhome.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.web.multipart.MultipartFile;
//import org.newhome.annotation.FilterAnnotation;
//import org.newhome.config.Constant;
//import org.newhome.config.FilterType;
//import org.newhome.info.UserInfo;
//import org.newhome.req.*;
//import org.newhome.res.DeletePicturesRes;
//import org.newhome.res.GetPicturesRes;
//import org.newhome.res.UploadPicturesRes;
//import org.newhome.service.IPictureDataService;
//import org.newhome.util.ResultBean;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * <p>
// * 前端控制器
// * </p>
// *
// * @author panyan
// * @since 2022-08-05
// */
//
//@Slf4j
//@Api(tags = "图像数据")
//@RestController
//@RequestMapping("/data")
//public class PictureDataController {
//
//    @Autowired
//    IPictureDataService iPictureDataService;
//
//    @Resource
//    private HttpServletResponse response;
//
//    @Resource
//    private HttpServletRequest request;
//
//
//    @CrossOrigin
//    @ApiOperation("删除图像")
//    @PostMapping("/deletepictures")
//    @FilterAnnotation(url = "/data/deletepictures", type = FilterType.login)
//    public ResultBean<DeletePicturesRes> deletePictures(@RequestBody DeletePicturesReq req) {
//        ResultBean<DeletePicturesRes> result = new ResultBean<>();
//        DeletePicturesRes res = new DeletePicturesRes();
//        iPictureDataService.deletePictures(req.getPictureids());
//        result.setMsg("删除成功");
//        result.setData(res);
//        return result;
//    }
//
//    public String upload(MultipartFile file, String savePath) {
//        File saveDir = new File(savePath);
//        System.out.println(savePath);
//        if(saveDir.exists() || saveDir.mkdirs()) {
//            System.out.println("success: " + savePath);
//        }
//        else{
//            return "";
//        }
//
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1,
//                file.getOriginalFilename().length());
//        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
//        String saveFile = savePath + "/" + filename;
//        try {
//            file.transferTo(new File(saveFile));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String appointFile = saveFile.substring(Constant.ORI_IMAGE_PATH.length(), saveFile.length());
//        return Constant.HOST + ":" + Constant.PORT + "/img/" + appointFile;
//    }
//
//    @CrossOrigin
//    @ApiOperation("上传图像")
//    @PostMapping("/uploadPictures")
//    @FilterAnnotation(url = "/data/uploadPictures", type = FilterType.login)
//    public ResultBean<UploadPicturesRes> uploadPictures(@RequestParam() Integer datasetId, @RequestParam(name = "pictures") MultipartFile[] pictures,  @CookieValue("userTicket")String ticket) throws IOException {
//        ResultBean<UploadPicturesRes> result = new ResultBean<>();
//        if(!StringUtils.hasText(ticket)){
//            result.setMsg("无用户登录");
//            result.setCode(ResultBean.NO_PERMISSION);
//            result.setData(null);
//            return result;
//        }
//        UploadPicturesRes res = new UploadPicturesRes();
//        List<String> savePaths = new ArrayList<>();
//        HttpSession httpSession = request.getSession();
//        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
//        String savePath = Constant.ORI_IMAGE_PATH + userInfo.getUsername() + '/' + datasetId;
//        for (MultipartFile f : pictures) {
//            String s = upload(f, savePath);
//            if(!StringUtils.hasText(s)){
//                result.setMsg("用户文件创建失败");
//                result.setCode(ResultBean.NO_PERMISSION);
//                result.setData(null);
//                return result;
//            }
//            savePaths.add(s);
//        }
//        log.info("savePath:" + savePath);
//        res.setSucceed(iPictureDataService.uploadPictures(savePaths, datasetId));
//        result.setData(res);
//        result.setMsg("上传成功");
//        return result;
//    }
//
//    @CrossOrigin
//    @ApiOperation("展示图像")
//    @PostMapping("/getPictures")
//    @FilterAnnotation(url = "/data/getPictures", type = FilterType.login)
//    public ResultBean<GetPicturesRes> getPictures(@RequestBody GetPicturesReq req) {
//        ResultBean<GetPicturesRes> result = new ResultBean<>();
//        GetPicturesRes res = new GetPicturesRes();
//        res.setPictures(iPictureDataService.getPictures(req.getDatasetId()));
//        result.setData(res);
//        result.setMsg("查询成功");
//        return result;
//    }
//
//}

package org.newhome.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.History;
import org.newhome.entity.Star;
import org.newhome.entity.User;
import org.newhome.entity.Video;
import org.newhome.req.AddHistoryReq;
import org.newhome.req.DeleteHistoriesReq;
import org.newhome.req.DeleteHistoryReq;
import org.newhome.req.GetHistoryReq;
import org.newhome.res.HistoriesRes;
import org.newhome.res.HistoryRes;
import org.newhome.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.newhome.service.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Api(tags = "历史记录")
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpServletRequest request;

    @CrossOrigin
    @ApiOperation("添加历史记录")
    @PostMapping("add")
    @FilterAnnotation(url = "/history/add", type = FilterType.auth)
    public ResultBean<HistoryRes> addHistory (AddHistoryReq historyReq){
        ResultBean<HistoryRes> result = new ResultBean<>();
        User user = userService.findById(historyReq.getUserId());
        if(user == null) {
            result.setMsg("用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        Video video  = videoService.findVideobyId(historyReq.getVideoId());
        if(video == null) {
            result.setMsg("视频不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        History history = historyService.getOne(historyReq.getUserId(), historyReq.getVideoId());
        if(history == null) {
            HistoryRes addHistoryRes = new HistoryRes();
            List<History> histories = historyService.getHistories(historyReq.getUserId());
            if(CollectionUtils.isEmpty(histories)) {
                if (histories.size() == 100) {
                    int lastHistoryId = histories.get(histories.size() - 1).getHistoryId();
                    historyService.deleteHistory(lastHistoryId);
                }
            }
            history.setVideoId(historyReq.getVideoId());
            history.setUserId(historyReq.getUserId());
            history.setWatchTime(historyReq.getWatchTime());
            int flag = historyService.addHistory(history);
            addHistoryRes.setHistory(history);
            if(flag != 0) {
                result.setMsg("插入浏览记录成功");
                result.setData(addHistoryRes);
            }
            else {
                result.setData(null);
                result.setMsg("插入浏览记录失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        else{
            history.setWatchTime(historyReq.getWatchTime());
            int flag = historyService.updateTime(history);
            HistoryRes res = new HistoryRes();
            res.setHistory(history);
            if(flag != 0) {
                result.setMsg("修改浏览记录时间成功");
                result.setData(res);
            }
            else {
                result.setData(null);
                result.setMsg("修改浏览记录时间失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("展示所有浏览记录")
    @PostMapping("findAll")
    @FilterAnnotation(url = "/history/findAll", type = FilterType.auth)
    public ResultBean<HistoriesRes> findHistories(@RequestBody GetHistoryReq req) {
        ResultBean<HistoriesRes> result = new ResultBean<>();
        HistoriesRes historiesRes = new HistoriesRes();
        User user = userService.findById(req.getUserId());
        if(user == null) {
            result.setMsg("用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        List<History> histories = historyService.getHistories(req.getUserId());
        if(CollectionUtils.isEmpty(histories)) {
            result.setMsg("查询失败！共0条记录");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            historiesRes.setHistories(histories);
            result.setData(historiesRes);
            result.setMsg("查询成功！共" + histories.size() + "条记录");
        }
        return result;
    }
    @CrossOrigin
    @ApiOperation("获取用户历史观看的视频")
    @GetMapping("getUserHistoryVideo")
    @FilterAnnotation(url="/history/getUserHistoryVideo", type = FilterType.auth)
    public ResultBean<List<Video>> getUserHistoryVideo(Integer userId ) {
        ResultBean<List<Video>> result = new ResultBean<>();
        User user = userService.findById(userId);
        if(user == null) {
            result.setMsg("该用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            List<Video> videoList = new ArrayList<>();
            List<History> histories =historyService.getHistories(userId);
            for(History history : histories){

                Video video = videoService.findVideobyId(history.getVideoId());
                if(video!=null){
                    videoList.add(video);
                }
            }
            if(!videoList.isEmpty()) {
                result.setData(videoList);
                result.setMsg("获取用户历史观看的视频成功");
            }
            else{
                result.setMsg("获取用户历史观看的视频失败");
                result.setCode(ResultBean.FAIL);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("删除单条浏览记录")
    @PostMapping("delete")
    @FilterAnnotation(url = "/history/delete", type = FilterType.login)
    public ResultBean<HistoryRes> deleteHistory(@RequestBody DeleteHistoryReq req){
        ResultBean<HistoryRes> result = new ResultBean<>();
        HistoryRes historyRes = new HistoryRes();
        History history = historyService.getById(req.getHistoryId());
        if(history == null){
            result.setCode(ResultBean.FAIL);
            result.setMsg("该条浏览记录不存在");
            result.setData(null);
        }
        else{
            int flag = historyService.deleteHistory(req.getHistoryId());
            historyRes.setHistory(history);
            if(flag != 0){
                result.setMsg("成功删除一条历史记录");
                result.setData(historyRes);
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
    @ApiOperation("删除多条浏览记录")
    @PostMapping("deleteAll")
    @FilterAnnotation(url = "/history/deleteAll", type = FilterType.login)
    public ResultBean<HistoryRes> deleteHistories(@RequestBody DeleteHistoriesReq req){
        ResultBean<HistoryRes> result = new ResultBean<>();
        HistoryRes historyRes = new HistoryRes();
        User user = userService.findById(req.getUserId());
        if(user == null) {
            result.setMsg("用户不存在！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        else{
            List<History> histories = historyService.getHistories(req.getUserId());
            int num = 0;
            for(History history:histories) {
                if(historyService.deleteHistory(history.getHistoryId()) != 0)
                    num ++;
            }
            if(num != 0){
                result.setMsg("成功删除"+histories.size()+"条历史记录成功");
                result.setData(historyRes);
            }
            else{
                result.setCode(ResultBean.FAIL);
                result.setMsg("删除0条历史记录");
                result.setData(null);
            }
        }
        return result;
    }

//
//    @CrossOrigin
//    @ApiOperation("下载历史记录")
//    @PostMapping("/downloadModel")
//    public ResultBean<ModelRes> download(@RequestBody DownloadModelReq req) {
//        ResultBean<ModelRes> result = new ResultBean<>();
//        ModelRes modelRes =  new ModelRes();
//        modelRes.setModelInfo(iModelService.findModelByName(req.getUsername(), req.getModel()));
//        if(modelRes.getModelInfo() == null) {
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//            result.setMsg("该条历史记录不存在！");
//        }
//        else {
//            //文件本地位置
//            String filePath = modelRes.getModelInfo().getModelPath();
//            int pos = filePath.lastIndexOf(".");
//            if(pos == -1){
//                result.setCode(ResultBean.FAIL);
//                result.setData(null);
//                result.setMsg("历史记录存储地址错误！");
//            }
//            else{
////        String filePath ="F:\\java\\Dam-Backend\\src\\main\\resources\\static\\model\\test.txt";
//                // 文件名称
//                String fileName = filePath.substring(pos);
//                File file = new File(filePath);
//                FileUtil.downloadFile(file, request, response, fileName);
//                result.setMsg("下载成功");
//                result.setData(modelRes);
//            }
//        }
//        return result;
//    }
//
//    @CrossOrigin
//    @ApiOperation("下载多个历史记录")
//    @PostMapping(value = "/downZip")
//    public ResultBean<ModelRes> downloadZipStream(@RequestBody DownloadModelReq req) {
//        ResultBean<ModelRes> result = new ResultBean<>();
//        ModelRes modelRes =  new ModelRes();
//        modelRes.setModelInfo(iModelService.findModelByName(req.getUsername(), req.getModel()));
//        if(modelRes.getModelInfo() == null) {
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//            result.setMsg("该条历史记录不存在！");
//            return result;
//        }
//        //文件本地位置
//        String basePath = modelRes.getModelInfo().getModelPath();
//        List<Map<String, String>> mapList = new ArrayList<>();
//        mapList = getFiles(basePath);
//        System.out.println(mapList);
//        FileUtil.zipDirFileToFile(mapList, request, response);
//        result.setMsg("下载成功");
//        result.setData(modelRes);
//        return result;
//    }
//
//    public static List<Map<String, String>> getFiles(String path) {
//        List<Map<String, String>> mapList = new ArrayList<>();
//        File file = new File(path);
//        // 如果这个路径是文件夹
//        if (file.isDirectory()) {
//            // 获取路径下的所有文件
//            File[] files = file.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                // 如果还是文件夹 递归获取里面的文件 文件夹
//                if (files[i].isDirectory()) {
//                    System.out.println("目录：" + files[i].getPath());
//                    mapList.addAll(getFiles(files[i].getPath()));
//
//                } else {
//                    Map<String, String> map = new HashMap<>();
//                    String fileName = files[i].getName();
//                    map.put("path", path+File.separator+fileName);
//                    map.put("name", fileName);
//                    mapList.add(map);
//                    System.out.println("文件：" + files[i].getName()); // files[i].getPath());
//                }
//            }
//
//        } else {
//            Map<String, String> map = new HashMap<>();
//            map.put("path",  file.getPath());
//            map.put("name", file.getName());
//            mapList.add(map);
//            System.out.println("文件：" + file.getPath());
//
//        }
//        return mapList;
//    }
}

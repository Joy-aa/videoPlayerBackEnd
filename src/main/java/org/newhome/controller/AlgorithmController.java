package org.newhome.controller;

import com.csvreader.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.newhome.config.FilterType;
import org.newhome.info.CrackInfo;
import org.newhome.info.PictureDataInfo;
import org.newhome.req.FindAlgoByNameReq;
import org.newhome.req.GetProcessReq;
import org.newhome.res.*;
import org.newhome.service.*;
import org.newhome.util.ResultBean;

import org.springframework.web.bind.annotation.*;

import org.newhome.info.DatasetInfo;
import org.newhome.req.CallAlgorithmReq;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */

@Slf4j
@Api(tags = "算法")
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
    @Autowired
    IAlgorithmService iAlgorithmService;

    @Autowired
    IDatasetService iDatasetService;
    @Autowired
    IPictureDataService iPictureDataService;

    @Autowired
    IHistoryService iHistoryService;

    @Autowired
    ICrackService iCrackService;

    @Resource
    private HttpServletRequest request;

    private Map<String, Integer> resolvedNum = new HashMap<String, Integer>();
    private Map<String, Integer> totalNum = new HashMap<String, Integer>();
    @CrossOrigin
    @ApiOperation("查询指定算法")
    @PostMapping("findAlgoByName")
    @FilterAnnotation(url = "/algorithm/findAlgoByName", type = FilterType.login)
    public ResultBean<FindAlgoByNameRes> findAlgo(@RequestBody FindAlgoByNameReq findAlgoByNameReq) {
        ResultBean<FindAlgoByNameRes> result = new ResultBean<FindAlgoByNameRes>();
        if(!StringUtils.hasText(findAlgoByNameReq.getAlgorithmName())){
            result.setCode(ResultBean.FAIL);
            result.setMsg("输入算法名称不合法！");
            result.setData(null);
            return result;
        }
        FindAlgoByNameRes  findAlgoByNameRes = new FindAlgoByNameRes();
        findAlgoByNameRes.setAlgorithmInfo(iAlgorithmService.findAlgoByName(findAlgoByNameReq.getAlgorithmName()));
        if(findAlgoByNameRes.getAlgorithmInfo() == null) {
            result.setCode(ResultBean.FAIL);
            result.setMsg("不存在该算法");
            result.setData(null);
        }
        else{
            result.setMsg("查找成功，算法名称为："+findAlgoByNameRes.getAlgorithmInfo().getAlgoName());
            result.setData(findAlgoByNameRes);
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("查询可用算法")
    @PostMapping("searchAlgorithm")
    @FilterAnnotation(url = "/algorithm/searchAlgorithm", type = FilterType.login)
    public ResultBean<SearchAlgorithmRes> searchAlgorithm(){
        SearchAlgorithmRes searchAlgorithmRes = new SearchAlgorithmRes();
        searchAlgorithmRes.setAlgorithmInfos(iAlgorithmService.searchAlgorithm());
        ResultBean<SearchAlgorithmRes> result = new ResultBean<SearchAlgorithmRes>();
        result.setData(searchAlgorithmRes);
        return result;
    }

    @CrossOrigin
    @ApiOperation("调用算法")
    @PostMapping("callAlgorithm")
    @FilterAnnotation(url = "/algorithm/callAlgorithm", type = FilterType.login)
    public ResultBean<CallAlgorithmRes> callAlgorithm(@RequestBody CallAlgorithmReq callAlgorithmReq) throws IOException {
        ResultBean<CallAlgorithmRes> result = new ResultBean<CallAlgorithmRes>();
        if(!StringUtils.hasText(callAlgorithmReq.getHistoryName())){
            result.setCode(ResultBean.FAIL);
            result.setMsg("历史记录名称不合法！");
            result.setData(null);
            return result;
        }
        resolvedNum.put(callAlgorithmReq.getHistoryName(), 0);
        totalNum.put(callAlgorithmReq.getHistoryName(), 0);
//        log.info("callAlgorithm:"+resolvedNum);
//        log.info("callAlgorithm:"+totalNum);
        DatasetInfo datasetInfo = iDatasetService.getDatasetInfo(callAlgorithmReq.getDatasetId());
        if(datasetInfo == null) {
            result.setCode(ResultBean.FAIL);
            result.setMsg("不存在该数据集");
            result.setData(null);
            return result;
        }
        if(datasetInfo.getDatasetIspublic() != 1 && !datasetInfo.getUsername().equals(callAlgorithmReq.getUserName())){
            result.setCode(ResultBean.NO_PERMISSION);
            result.setMsg("非公开数据集");
            result.setData(null);
            return result;
        }

        List<PictureDataInfo> pictures = iPictureDataService.getPictures(datasetInfo.getDatasetId());
        log.info("callAlgorithmReq:" + callAlgorithmReq);
        log.info("pictures:"+pictures);
        int size1 = pictures.size();
        if(size1 < 1) {
            result.setCode(ResultBean.FAIL);
            result.setMsg("数据集中没有图像数据");
            result.setData(null);
            return result;
        }
        totalNum.put(callAlgorithmReq.getHistoryName(), size1);
        String url = pictures.get(0).getPictureUrl();
        int beginIndex = url.indexOf("/img/") + 5;
        int endIndex = url.lastIndexOf("/") + 1;

        String urlPath = Constant.HOST + ":" + Constant.PORT + "/img/" + url.substring(beginIndex, endIndex);
        String datasetPath =  Constant.ORI_IMAGE_PATH + url.substring(beginIndex, endIndex);
        String savePath = Constant.RESULT_PATH + callAlgorithmReq.getUserName() + '/' + callAlgorithmReq.getDatasetId() + '/';
        log.info("urlPath:" + urlPath);
        log.info("datasetPath："+ datasetPath);
        log.info("savePath:" + savePath);
        File saveDir = new File(savePath);
        if(saveDir.exists() || saveDir.mkdirs()) {
            System.out.println("success: " + savePath);
        }
        else{
            result.setCode(ResultBean.FAIL);
            result.setMsg("用户文件夹创建失败");
            result.setData(null);
            return result;
        }

        String command1 = Constant.PYTHON_EXEC + Constant.PYTHON_EXEC_FILE;
        String args = " --dataset_dir " + datasetPath  + " --result_dir " + savePath + " --host " + Constant.HOST + " --port 50006";
        String command2 = command1 + args;
        System.out.println(command2);
        Process process = Runtime.getRuntime().exec(command2);

        int Num = 0;
        ServerSocket ss = new ServerSocket(50006);
        while(Num < size1) {
            System.out.println("启动服务器....");
            Socket s = ss.accept();
            System.out.println("客户端:"+ InetAddress.getLocalHost()+"已连接到服务器");
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String mess = br.readLine();
            if(StringUtils.hasText(mess)){
                log.info("mess:" + mess);
                Num += 1;
            }
            else continue;
            System.out.println("已处理图像:" +Num);
            resolvedNum.put(callAlgorithmReq.getHistoryName(), Num);
            if("SOS".equals(mess)) {
                result.setCode(ResultBean.FAIL);
                result.setMsg("图像数据处理失败");
                result.setData(null);
                ss.close();
                return result;
            }
            PictureDataInfo pictureDataInfo = iPictureDataService.getPictureByUrl(urlPath + mess);
            System.out.println(urlPath+mess);
            System.out.println(pictureDataInfo);
            String saveUrl = Constant.HOST + ":" + Constant.PORT + "/res/" + callAlgorithmReq.getUserName() + '/'
                    + callAlgorithmReq.getDatasetId() + '/' + mess;
            iPictureDataService.updatePictureResult(pictureDataInfo.getPictureId(), saveUrl);
            iCrackService.deleteCrackByPictureId(pictureDataInfo.getPictureId());


            String csvPath = savePath + mess.substring(0,mess.indexOf('.')) + ".csv";
            List<CrackInfo> crackInfos = readCrackFile(csvPath, pictureDataInfo.getPictureId());
            if(!iCrackService.addCracks(crackInfos)){
                result.setCode(ResultBean.FAIL);
                result.setMsg("裂缝信息添加失败");
                result.setData(null);
                return result;
            }

        }
        ss.close();
        result.setMsg("已处理"+size1+"张图片");
        return result;
    }

    public List<CrackInfo> readCrackFile(String csvFile, int pictureId) throws IOException {
        List<CrackInfo> crackInfos = new ArrayList<>();
        CsvReader csvReader = new CsvReader(csvFile);
        csvReader.readHeaders();
        while (csvReader.readRecord()) {
            CrackInfo crackInfo = new CrackInfo();
            // 通过表头的文字获取
            crackInfo.setCrackAvgwidth(Float.valueOf(csvReader.get("average_width")));
            crackInfo.setCrackLength(Float.valueOf(csvReader.get("length")));
            crackInfo.setCrackMaxwidth(Float.valueOf(csvReader.get("max_width")));
            crackInfo.setCrackXmin(Integer.valueOf(csvReader.get("xmin")));
            crackInfo.setCrackXmax(Integer.valueOf(csvReader.get("xmax")));
            crackInfo.setCrackYmax(Integer.valueOf(csvReader.get("ymax")));
            crackInfo.setCrackYmin(Integer.valueOf(csvReader.get("ymin")));
            crackInfo.setPictureId(pictureId);
            crackInfos.add(crackInfo);
        }
        return crackInfos;
    }

    @CrossOrigin
    @ApiOperation("读取进度")
    @PostMapping("getProcess")
    @FilterAnnotation(url = "/algorithm/getProcess", type = FilterType.login)
    public ResultBean<GetProcessRes> getProcess(@RequestBody GetProcessReq getProcessReq) {
//        log.info("getProcessReq"+getProcessReq);
//        log.info("getProcess:"+resolvedNum);
//        log.info("getProcess:"+totalNum);
        ResultBean<GetProcessRes> resultBean = new ResultBean<>();
        GetProcessRes getProcessRes = new GetProcessRes();
        getProcessRes.setProcess(resolvedNum.get(getProcessReq.getHistoryName()));
        getProcessRes.setTotal(totalNum.get(getProcessReq.getHistoryName()));
        resultBean.setData(getProcessRes);

        return resultBean;
    }
}

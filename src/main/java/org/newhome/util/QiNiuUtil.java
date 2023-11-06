package org.newhome.util;

import com.qiniu.common.QiniuException;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;


public class QiNiuUtil {

    private static String accessKey = "cjph6i_nsZJwxelLwEqaj4dlknNKEI94oVpRuRQF";
    private static String secretKey = "ulCAHAVVI62MuiwlL9yHg-FNrbtRw5dZqJb1SyiL";

    public static String getDownLoadVideoUrl(String fileName) {
        String bucketDomain  = "http://s3604nf5a.hn-bkt.clouddn.com";
        String finalUrl ="";

        String publicUrl = String.format("%s/%s", bucketDomain, fileName);
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600; // 1小时，可以自定义链接过期时间
        finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }

    public static String getHeadShotUrl(String fileName) {
        String bucketDomain  = "http://s3kd3gnyi.hn-bkt.clouddn.com";
        String finalUrl ="";

        String publicUrl = String.format("%s/%s", bucketDomain, fileName);
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600; // 1小时，可以自定义链接过期时间
        finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }

    //生成视频后进行视频截帧
    public static int generateVideoPageShot(String bucket, String key) {
//        String newBucket = "http://s318q0lql.hn-bkt.clouddn.com";
        //s318q0lql.hn-bkt.clouddn.com

        Auth auth = Auth.create(accessKey, secretKey);

        String filename = key.substring(0, key.indexOf('.'));

//数据处理指令，支持多个指令
//        String saveMp4Entry = String.format("%s:avthumb_test_target.mp4", bucket);
        String saveJpgEntry = String.format("%s:%s.jpg", bucket, filename);
//        String avthumbMp4Fop = String.format("avthumb/mp4|saveas/%s", UrlSafeBase64.encodeToString(saveMp4Entry));
        String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));
//将多个数据处理指令拼接起来
        String persistentOpfs = StringUtils.join(new String[]{
                vframeJpgFop
        }, ";");

//数据处理队列名称，必须
        String persistentPipeline = "default.sys";
//数据处理完成结果通知地址
        String persistentNotifyUrl = "http://api.example.com/qiniu/pfop/notify";

//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
//...其他参数参考类注释

//构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        OperationStatus operationStatus = new OperationStatus();
        try {
            String persistentId = operationManager.pfop(bucket, key, persistentOpfs, persistentPipeline, persistentNotifyUrl, true);
            //可以根据该 persistentId 查询任务处理进度
//            System.out.println(persistentId);

            operationStatus = operationManager.prefop(bucket,persistentId);
            while (operationStatus.code == 1 || operationStatus.code == 2)
            {
                //解析 operationStatus 的结果
                operationStatus = operationManager.prefop(bucket, persistentId);
//                System.out.println(operationStatus.code);
                Thread.sleep(1000);
            }
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
//            System.err.println(e.response.getInfo());
//            System.err.println(e.getCause());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return operationStatus.code;
    }

    public static void main(String[] args) {
        String bucket = "web-shortvideo";
        String key  = "BV12B4y1R7Fs.mp4";
        generateVideoPageShot(bucket, key);
    }


}

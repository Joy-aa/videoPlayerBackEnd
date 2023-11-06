package org.newhome.util;

import com.qiniu.util.Auth;

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

    public static String getHeadShotVideoUrl(String fileName) {
        String bucketDomain  = "http://s3kd3gnyi.hn-bkt.clouddn.com";
        String finalUrl ="";

        String publicUrl = String.format("%s/%s", bucketDomain, fileName);
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600; // 1小时，可以自定义链接过期时间
        finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }


}

package org.newhome.util;

import org.newhome.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.newhome.util.MD5Util.formPassToDBPass;

public class DataGenerator {

    public static void main(String[] args) {
//        int length = 10;
//        System.out.println(getStringRandom(length));
//        List<User> users = new ArrayList<>();
//        for(int i = 101; i <= 200; i++) {
//            User user = new User();
//            user.setUsername(getStringRandom(10));
//            user.setHeadshotname("headshot"+(i-100)+".jpg");
//            user.setSalt(MD5Util.getSalt());
//            user.setPassword(formPassToDBPass("123", user.getSalt()));
//            user.setEmail(i+"@qq.com");
//        }
    }

     //自动生成名字(中文)
      public static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
          return ret;
      }

      //生成随机用户名，数字和字母组成,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
    //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


}

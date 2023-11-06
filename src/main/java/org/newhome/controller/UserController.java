package org.newhome.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.newhome.annotation.FilterAnnotation;
import org.newhome.config.FilterType;
import org.newhome.entity.Relation;
import org.newhome.entity.User;
import org.newhome.entity.Video;
import org.newhome.req.*;
import org.newhome.res.CaptchaRes;
import org.newhome.res.LoginRes;
import org.newhome.res.RegisterRes;
import org.newhome.res.UserRes;
import org.newhome.service.RelationService;
import org.newhome.service.UserService;
import org.newhome.service.VideoService;
import org.newhome.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.newhome.util.Base64Util.encode;
import static org.newhome.util.MD5Util.formPassToDBPass;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author panyan
 * @since 2022-08-05
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RelationService relationService;
    @Autowired
    VideoService videoService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpServletRequest request;


    public static final String staticPath = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("static")).getPath();
    private String saveHeadImg(MultipartFile file) {
        String defaultHead = staticPath + File.separator +"head" + File.separator + "img.jpg";
        if (file == null) {
            //若用户没有上传头像，则使用默认的头像
            System.out.println("imgUrl: empty");
            return defaultHead;
        }
        // 原始文件名
        String originalFileName = file.getOriginalFilename();
        // 获取图片后缀
        assert originalFileName != null;
        if(originalFileName.lastIndexOf(".") == -1) return defaultHead;
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 生成图片存储的名称，UUID 避免相同图片名冲突，并加上图片后缀
        String fileName = UUID.randomUUID().toString() + suffix;
        // 图片存储目录及图片名称
        String url_path = "head" + File.separator + fileName;
        //图片保存路径
        String savePath = staticPath + File.separator + url_path;
        System.out.println("图片保存地址："+savePath);

        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
            return savePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultHead;
    }

    @CrossOrigin
    @ApiOperation("用户注册")
    @PostMapping("register")
    @FilterAnnotation(url="/user/register",type = FilterType.anno)
    public ResultBean<RegisterRes> Register(RegisterReq registerReq, MultipartFile file) {
        ResultBean<RegisterRes> result = new ResultBean<>();
        //验证验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(registerReq.getUsername().isEmpty()) {
            result.setMsg("注册失败！用户名为空");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if(registerReq.getPassword().isEmpty()) {
            result.setMsg("注册失败！密码为空");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if(registerReq.getEmail().isEmpty()) {
            result.setMsg("注册失败！邮箱为空");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else if (registerReq.getCode().isEmpty() || !registerReq.getCode().equals(captcha)){
            result.setMsg("验证码错误！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
        else {
            RegisterRes registerRes = new RegisterRes();
            registerRes.setUser(userService.findByEmail(registerReq.getEmail()));
            if(registerRes.getUser() != null) {
                result.setMsg("注册失败！邮箱已存在");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
            else{
                User user = new User();
                user.setHeadshot(this.saveHeadImg(file));
                user.setUsername(registerReq.getUsername());
                user.setSalt(MD5Util.getSalt());
                user.setPassword(formPassToDBPass(registerReq.getPassword(), user.getSalt()));
                user.setEmail(registerReq.getEmail());
                registerRes.setUser(userService.addUser(user));
                result.setMsg("注册成功！");
                result.setData(registerRes);
            }
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("获取验证码")
    @GetMapping(value = "/captcha")
    @FilterAnnotation(url="/user/captcha",type = FilterType.anno)
    public ResultBean<CaptchaRes> captcha(){
        ResultBean<CaptchaRes> result = new ResultBean<>();
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码:  " + text);
        //将验证码放到session中
        request.getSession().setAttribute("captcha",text);
        //根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
            result.setMsg("图片转换失败");
            result.setCode(ResultBean.FAIL);
            return result;
        }
        finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] imageByte = outputStream.toByteArray();
        String base64 = encode(imageByte);
        CaptchaRes captchaRes = new CaptchaRes();
        captchaRes.setCode(text);
        captchaRes.setBase64(base64);
        result.setData(captchaRes);
        result.setMsg("返回验证码和图片base64编码");
        return result;
    }


    @CrossOrigin
    @ApiOperation("用户登录")
    @PostMapping("login")
    @FilterAnnotation(url="/user/login",type = FilterType.anno)
    public ResultBean<LoginRes> login(@RequestBody LoginReq req){
        ResultBean<LoginRes> result = new ResultBean<>();
        System.out.println(req);
        //验证验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println(captcha);
        if (!StringUtils.hasText(captcha) || !captcha.equals(req.getCode())){
            result.setMsg("验证码错误！");
            System.out.println("验证码错误！");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
            return result;
        }
//        if (!req.getCode().equals(captcha)){
//            result.setMsg("验证码错误！");
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//            return result;
//        }
        //验证账号密码
        LoginRes loginRes = new LoginRes();
        loginRes.setUser(userService.findByEmail(req.getEmail()));
        System.out.println(loginRes);
        if (loginRes.getUser()!=null){
            String salt = loginRes.getUser().getSalt();
            if(loginRes.getUser().getPassword().equals(formPassToDBPass(req.getPassword(), salt))){
                result.setMsg("登录成功！");
                //生成cookie
                System.out.println("登录成功！");
                String userTicket = UUIDUtil.uuid();
                request.getSession().setAttribute(userTicket, loginRes.getUser());
                request.getSession().setMaxInactiveInterval(4*60*60);
                CookieUtil.setCookie(request, response, "userTicket", userTicket);
                result.setData(loginRes);
            } else {
                result.setCode(ResultBean.FAIL);
                result.setMsg("密码错误！");
                System.out.println("密码错误！");
                result.setData(null);
            }
        } else {
            result.setMsg("用户不存在！");
            result.setCode(ResultBean.NO_PERMISSION);
            System.out.println("用户不存在！");
            result.setData(null);
        }
        return result;
    }

    @CrossOrigin
    //登出功能
    @ApiOperation("用户退出")
    @GetMapping("logout")
    @FilterAnnotation(url = "/user/logout", type = FilterType.login)
    public ResultBean<LoginRes> logout(@CookieValue("userTicket")String ticket){
        ResultBean<LoginRes> result = new ResultBean<>();
        HttpSession httpSession = request.getSession();
        User userInfo = (User)httpSession.getAttribute(ticket);
        httpSession.removeAttribute(ticket);
        LoginRes loginRes = new LoginRes();
        loginRes.setUser(userInfo);
        result.setData(loginRes);
        result.setMsg("logout success!");
        return result;
    }

    //修改个人密码
    @CrossOrigin
    @ApiOperation("修改密码")
    @PostMapping("updatePwd")
    @FilterAnnotation(url = "/user/updatePwd", type = FilterType.login)
    public ResultBean<LoginRes> updatePwd(UpdatePwdReq updatePwdReq, @CookieValue("userTicket")String ticket) {
        ResultBean<LoginRes> result = new ResultBean<>();
        LoginRes loginRes = new LoginRes();
        loginRes.setUser(userService.findByEmail(updatePwdReq.getEmail()));
        System.out.println(loginRes);
        if (loginRes.getUser()!=null){
            String salt = loginRes.getUser().getSalt();
            if(loginRes.getUser().getPassword().equals(formPassToDBPass(updatePwdReq.getPassword(), salt))){
//                result.setMsg("登录成功");
//                //生成cookie
//                String userTicket = UUIDUtil.uuid();
//                request.getSession().setAttribute(userTicket, loginRes.getUser());
//                request.getSession().setMaxInactiveInterval(4*60*60);
//                CookieUtil.setCookie(request, response, "userTicket", userTicket);
//                result.setData(loginRes);
                String newPwd = formPassToDBPass(updatePwdReq.getNewPassword(), loginRes.getUser().getSalt());
                userService.updateUserPassword(updatePwdReq.getEmail(), newPwd);
                loginRes.getUser().setEmail(updatePwdReq.getNewPassword());
                result.setMsg("修改密码成功！");
                result.setData(loginRes);
            } else {
                result.setCode(ResultBean.FAIL);
                result.setMsg("密码错误");
                result.setData(null);
            }
        } else {
            result.setMsg("用户不存在");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        return result;
    }

    //修改邮箱
    @CrossOrigin
    @ApiOperation("修改邮箱")
    @PostMapping("updateEmail")
    @FilterAnnotation(url = "/user/updateEmail", type = FilterType.login)
    public ResultBean<LoginRes> updateEmail(UpdateEmailReq updateEmailReq, @CookieValue("userTicket")String ticket) {
        ResultBean<LoginRes> result = new ResultBean<>();
        LoginRes loginRes = new LoginRes();
        loginRes.setUser(userService.findByEmail(updateEmailReq.getEmail()));
        System.out.println(loginRes);
        if (loginRes.getUser()!=null){
            String salt = loginRes.getUser().getSalt();
            if(loginRes.getUser().getPassword().equals(formPassToDBPass(updateEmailReq.getPassword(), salt))){
//                result.setMsg("登录成功");
//                //生成cookie
//                String userTicket = UUIDUtil.uuid();
//                request.getSession().setAttribute(userTicket, loginRes.getUser());
//                request.getSession().setMaxInactiveInterval(4*60*60);
//                CookieUtil.setCookie(request, response, "userTicket", userTicket);
//                result.setData(loginRes);
                userService.updateUserEmail(updateEmailReq.getEmail(), updateEmailReq.getNewEmail());
                loginRes.getUser().setEmail(updateEmailReq.getNewEmail());
                result.setMsg("修改邮箱成功！");
                result.setData(loginRes);
            } else {
                result.setCode(ResultBean.FAIL);
                result.setMsg("密码错误");
                result.setData(null);
            }
        } else {
            result.setMsg("用户不存在");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        return result;
    }

    //修改个人信息
    @CrossOrigin
    @ApiOperation("修改个人信息")
    @PostMapping("updateUser")
    @FilterAnnotation(url = "/user/updateUser", type = FilterType.login)
    public ResultBean<User> updateUser(UpdateUserReq updateUserReq) {
        ResultBean<User> result = new ResultBean<>();
        User olduser = userService.findByEmail(updateUserReq.getEmail());

        if(olduser != null) {
            User newuser = new User();
            newuser.setEmail(updateUserReq.getEmail());
            if (updateUserReq.getUsername().isEmpty())
                newuser.setUsername(olduser.getUsername());
            else
                newuser.setUsername(updateUserReq.getUsername());
            if (updateUserReq.getHeadshot().isEmpty())
                newuser.setHeadshot(olduser.getHeadshot());
            else
                newuser.setHeadshot(updateUserReq.getHeadshot());
            if (updateUserReq.getIntroduction().isEmpty())
                newuser.setIntroduction(olduser.getIntroduction());
            else
                newuser.setIntroduction(updateUserReq.getIntroduction());

            int res = userService.updateUser(newuser);
            if(res != -1){
                result.setMsg("修改个人信息成功！");
                result.setData(newuser);
            }
            else{
                result.setMsg("修改个人信息失败，请重新操作");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        else{
            result.setMsg("修改个人信息失败！用户不存在");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        return result;
    }
    @CrossOrigin
    @ApiOperation("修改个人信息和头像")
    @PostMapping("updateUserHeadShot")
    @FilterAnnotation(url = "/user/updateUserHeadShot", type = FilterType.login)
    public ResultBean<User> updateUserHeadShot(UpdateUserHeadShotReq updateUserheadshotReq) {
        ResultBean<User> result = new ResultBean<>();
        User olduser = userService.findByEmail(updateUserheadshotReq.getEmail());

        if(olduser != null) {
            User newuser = new User();
            newuser.setEmail(updateUserheadshotReq.getEmail());
            if (updateUserheadshotReq.getUsername().isEmpty())
                newuser.setUsername(olduser.getUsername());
            else
                newuser.setUsername(updateUserheadshotReq.getUsername());
            if (updateUserheadshotReq.getHeadshotname().isEmpty())
                newuser.setHeadshotname(olduser.getHeadshotname());
            else
                newuser.setHeadshotname(updateUserheadshotReq.getHeadshotname());

            if (updateUserheadshotReq.getIntroduction().isEmpty())
                newuser.setIntroduction(olduser.getIntroduction());
            else
                newuser.setIntroduction(updateUserheadshotReq.getIntroduction());

            int res = userService.updateUserImg(newuser);
            if(res != -1){
                result.setMsg("修改个人信息成功！");
                result.setData(newuser);
            }
            else{
                result.setMsg("修改个人信息失败，请重新操作");
                result.setCode(ResultBean.FAIL);
                result.setData(null);
            }
        }
        else{
            result.setMsg("修改个人信息失败！用户不存在");
            result.setCode(ResultBean.NO_PERMISSION);
            result.setData(null);
        }
        return result;
    }

    // 根据指定信息查询个人信息
    @CrossOrigin
    @ApiOperation("根据用户名和简介模糊查询")
    @GetMapping("findUsers")
    @FilterAnnotation(url="/user/findUsers",type = FilterType.login)
    public ResultBean<List<UserRes>> findUsers(String content) {
        List<User> userList = userService.findUsers(content);
        List<UserRes> userResList = new ArrayList<>();
        ResultBean<List<UserRes>> result = new ResultBean<>();
        for (User user: userList) {
            UserRes ur1 = new UserRes(user);
            List<Video> videoList = videoService.findVideoByUser(user);
            int num = 0;
            for (Video video: videoList) {
                num += video.getLikeNum();
            }
            ur1.setLikeNum(num);
            List<Relation> fansList = relationService.findFans(ur1.getUserId(), 0);
            ur1.setFanNum(fansList.size());
            userResList.add(ur1);
        }
        result.setMsg("查询成功");
        result.setData(userResList);
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据id查询单个用户（不更新头像）")
    @GetMapping("findUser")
    @FilterAnnotation(url="/user/findUser",type = FilterType.anno)
    public ResultBean<User> findUser(Integer userId) {
        ResultBean<User> result = new ResultBean<>();
        User user = userService.findById(userId);
        if(user == null) {
            result.setMsg("用户不存在");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            result.setData(user);
            result.setMsg("查询成功");
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("根据id查询单个用户（更新头像）")
    @GetMapping("findUserUpdatHeadShot")
    @FilterAnnotation(url="/user/findUserUpdatHeadShot",type = FilterType.anno)
    public ResultBean<User> findUserUpdatHeadShot(Integer userId) {
        VideoController vc = new VideoController();
        ResultBean<User> result = new ResultBean<>();
        User user = userService.findById(userId);
        if(user == null) {
            result.setMsg("用户不存在");
            result.setCode(ResultBean.FAIL);
            result.setData(null);
        }
        else {
            String headShotName = user.getHeadshotname();
            user.setHeadshot(vc.getDownLoadVideoUrl(headShotName));
            result.setData(user);
            result.setMsg("查询成功");
        }
        return result;
    }

    @CrossOrigin
    @ApiOperation("生成100个用户")
    @GetMapping("generate")
    public void userGenerate() {
        for(int i = 101; i <= 200; i++) {
            User user = new User();
            user.setUsername(DataGenerator.getStringRandom(10));
            user.setHeadshotname("headshot"+(i-100)+".jpg");
            user.setSalt(MD5Util.getSalt());
            user.setPassword(formPassToDBPass("123", user.getSalt()));
            user.setEmail(i+"@qq.com");
            userService.addUser(user);
        }
    }
}

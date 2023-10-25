//package org.newhome.controller;
//
//
//import com.google.code.kaptcha.impl.DefaultKaptcha;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ClassUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.web.multipart.MultipartFile;
//import org.newhome.annotation.FilterAnnotation;
//import org.newhome.config.FilterType;
//import org.newhome.info.UserInfo;
//import org.newhome.req.*;
//import org.newhome.res.*;
//import org.newhome.service.IUserService;
//import org.newhome.util.CookieUtil;
//import org.newhome.util.MD5Util;
//import org.newhome.util.ResultBean;
//import org.newhome.util.UUIDUtil;
//
//import javax.annotation.Resource;
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//import static org.newhome.util.Base64Util.encode;
//import static org.newhome.util.MD5Util.formPassToDBPass;
//
///**
// * <p>
// * 前端控制器
// * </p>
// *
// * @author panyan
// * @since 2022-08-05
// */
//@Api(tags = "用户")
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    @Autowired
//    IUserService iUserService;
//
//    @Autowired
//    private DefaultKaptcha defaultKaptcha;
//
//    @Resource
//    private HttpServletResponse response;
//
//    @Resource
//    private HttpServletRequest request;
//
//    public static final String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
//    private String saveHeadImg(MultipartFile file) {
//        String defaultHead = staticPath + File.separator +"head" + File.separator + "img.jpg";
//        if (file == null) {
//            //若用户没有上传头像，则使用默认的头像
//            System.out.println("imgUrl: empty");
//            return defaultHead;
//        }
//        // 原始文件名
//        String originalFileName = file.getOriginalFilename();
//        // 获取图片后缀
//        if(originalFileName.lastIndexOf(".") == -1) return defaultHead;
//        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
//        // 生成图片存储的名称，UUID 避免相同图片名冲突，并加上图片后缀
//        String fileName = UUID.randomUUID().toString() + suffix;
//        // 图片存储目录及图片名称
//        String url_path = "head" + File.separator + fileName;
//        //图片保存路径
//        String savePath = staticPath + File.separator + url_path;
//        System.out.println("图片保存地址："+savePath);
//
//        File saveFile = new File(savePath);
//        if (!saveFile.exists()){
//            saveFile.mkdirs();
//        }
//        try {
//            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
//            return savePath;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return defaultHead;
//    }
//
//    @CrossOrigin
//    @ApiOperation("用户注册")
//    @PostMapping("register")
//    @FilterAnnotation(url="/user/register",type = FilterType.anno)
//    public ResultBean<RegisterRes> Register(RegisterReq registerReq, MultipartFile file) {
//        ResultBean<RegisterRes> result = new ResultBean<>();
//        //验证验证码
//        String captcha = (String) request.getSession().getAttribute("captcha");
//        if (!StringUtils.hasText(registerReq.getCode())|| !captcha.equals(registerReq.getCode())){
//            result.setMsg("验证码错误！");
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//            return result;
//        }
//        if(!StringUtils.hasText(registerReq.getUsername())) {
//            result.setMsg("注册失败！用户名为空");
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//        }
//        else {
//            RegisterRes registerRes = new RegisterRes();
//            registerRes.setUserInfo(iUserService.findByUsername(registerReq.getUsername()));
//            if(registerRes.getUserInfo() != null) {
//                result.setMsg("注册失败！用户名已存在");
//                result.setCode(ResultBean.FAIL);
//                result.setData(null);
//            }
//            else{
//                UserInfo userInfo = new UserInfo();
//                userInfo.setHeadshot(this.saveHeadImg(file));
//                userInfo.setUsername(registerReq.getUsername());
//                userInfo.setSalt(MD5Util.getSalt());
//                userInfo.setPassword(formPassToDBPass(registerReq.getPassword(), userInfo.getSalt()));
//                userInfo.setTelephone(registerReq.getTelephone());
//                userInfo.setAuthority(registerReq.getAuthority());
//                registerRes.setUserInfo(iUserService.addUser(userInfo));
//                result.setMsg("注册成功！");
//                result.setData(registerRes);
//            }
//        }
//        return result;
//    }
//
//    @CrossOrigin
//    @ApiOperation("获取验证码")
//    @GetMapping(value = "/captcha")
//    @FilterAnnotation(url="/user/captcha",type = FilterType.anno)
//    public ResultBean<CaptchaRes> captcha(){
//        ResultBean<CaptchaRes> result = new ResultBean<>();
//        //获取验证码文本内容
//        String text = defaultKaptcha.createText();
//        System.out.println("验证码:  " + text);
//        //将验证码放到session中
//        request.getSession().setAttribute("captcha",text);
//        //根据文本内容创建图形验证码
//        BufferedImage image = defaultKaptcha.createImage(text);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(image,"jpg",outputStream);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            result.setMsg("图片转换失败");
//            result.setCode(ResultBean.FAIL);
//            return result;
//        }
//        finally {
//            if (outputStream != null){
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        byte[] imageByte = outputStream.toByteArray();
//        String base64 = encode(imageByte);
//        CaptchaRes captchaRes = new CaptchaRes();
//        captchaRes.setCode(text);
//        captchaRes.setBase64(base64);
//        result.setData(captchaRes);
//        result.setMsg("返回验证码和图片base64编码");
//        return result;
//    }
//
//
//    @CrossOrigin
//    @ApiOperation("用户登录")
//    @PostMapping("login")
//    @FilterAnnotation(url="/user/login",type = FilterType.anno)
//    public ResultBean<LoginRes> login(@RequestBody LoginReq req){
//        ResultBean<LoginRes> result = new ResultBean<>();
//        System.out.println(req);
//        //验证验证码
//        String captcha = (String) request.getSession().getAttribute("captcha");
//        System.out.println(captcha);
//        if (!StringUtils.hasText(captcha) || !captcha.equals(req.getCode())){
//            result.setMsg("验证码错误，请重新获取验证码");
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//            return result;
//        }
////        if (!req.getCode().equals(captcha)){
////            result.setMsg("验证码错误！");
////            result.setCode(ResultBean.FAIL);
////            result.setData(null);
////            return result;
////        }
//        //验证账号密码
//        LoginRes loginRes = new LoginRes();
//        loginRes.setUserInfo(iUserService.findByUsername(req.getUsername()));
//        System.out.println(loginRes);
//        if (loginRes.getUserInfo()!=null){
//            String salt = loginRes.getUserInfo().getSalt();
//            if(loginRes.getUserInfo().getPassword().equals(formPassToDBPass(req.getPassword(), salt))){
//                result.setMsg("登陆成功");
//                //生成cookie
//                String userTicket = UUIDUtil.uuid();
//                request.getSession().setAttribute(userTicket, loginRes.getUserInfo());
//                request.getSession().setMaxInactiveInterval(4*60*60);
//                CookieUtil.setCookie(request, response, "userTicket", userTicket);
//                result.setData(loginRes);
//            } else {
//                result.setCode(ResultBean.FAIL);
//                result.setMsg("用户名或密码错误");
//                result.setData(null);
//            }
//        } else {
//            result.setMsg("用户不存在");
//            result.setCode(ResultBean.FAIL);
//            result.setData(null);
//        }
//        return result;
//    }
//
//    @CrossOrigin
//    //登出功能
//    @ApiOperation("用户退出")
//    @GetMapping("logout")
//    @FilterAnnotation(url = "/user/updatepwd", type = FilterType.login)
//    public ResultBean<LoginRes> unLogin( @CookieValue("userTicket")String ticket){
//        ResultBean<LoginRes> result = new ResultBean<>();
//        HttpSession httpSession = request.getSession();
//        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
//        httpSession.removeAttribute(ticket);
//        LoginRes loginRes = new LoginRes();
//        loginRes.setUserInfo(userInfo);
//        result.setData(loginRes);
//        result.setMsg("logout success!");
//        return result;
//    }
//
//    //    根据用户名查询个人信息
//    @CrossOrigin
//    @ApiOperation("查询指定用户")
//    @PostMapping("find")
//    @FilterAnnotation(url="/user/find",type = FilterType.auth)
//    public ResultBean<UserRes> find(@RequestBody UserReq userReq) {
//        UserRes userRes = new UserRes();
//        userRes.setUserInfo(iUserService.findByUsername(userReq.getUsername()));
//        ResultBean<UserRes> result = new ResultBean<>();
//        if(userRes.getUserInfo()!=null) {
//            String salt = userRes.getUserInfo().getSalt();
//            if(userRes.getUserInfo().getPassword().equals(formPassToDBPass(userReq.getPassword(), salt))){
//                result.setMsg("查询成功");
//                userRes.getUserInfo().setPassword(null);//获取信息不必返回密码
//                result.setData(userRes);
//            }
//        }
//        else {
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("用户不存在");
//            result.setData(null);
//        }
//        return result;
//    }
//
//    //修改个人密码
//    @CrossOrigin
//    @ApiOperation("修改密码")
//    @PostMapping("updatepwd")
//    @FilterAnnotation(url = "/user/updatepwd", type = FilterType.login)
//    public ResultBean<UpdatepwdRes> updatepwd(UpdatepwdReq updatepwdReq, @CookieValue("userTicket")String ticket) {
//        UpdatepwdRes updatepwdRes = new UpdatepwdRes();
//        ResultBean<UpdatepwdRes> result = new ResultBean<>();
//        HttpSession httpSession = request.getSession();
//        UserInfo userInfo = (UserInfo)httpSession.getAttribute(ticket);
//        updatepwdRes.setUserInfo(iUserService.findByUsername(updatepwdReq.getUsername()));
//        if(updatepwdRes.getUserInfo() != null) {
//                    if(userInfo.getPassword().equals(updatepwdRes.getUserInfo().getPassword())){
//                        String newPwd = formPassToDBPass(updatepwdReq.getNewPassword(), userInfo.getSalt());
//                        iUserService.updateUserPassword(updatepwdReq.getUsername(), newPwd);
//                        updatepwdRes.getUserInfo().setPassword(newPwd);
//                        result.setMsg("修改密码成功！");
//                        result.setData(updatepwdRes);
//                    }
//                    else{
//                        result.setMsg("用户访问错误，请跳转login页面");
//                        result.setCode(ResultBean.NO_PERMISSION);
//                        result.setData(null);
//                    }
//        }
//        else{
//                    result.setMsg("修改密码失败！用户不存在");
//                    result.setCode(ResultBean.FAIL);
//                    result.setData(null);
//        }
//        return  result;
//    }
//
//
//    //修改手机号
//    @CrossOrigin
//    @ApiOperation("修改手机号")
//    @PostMapping("updatetel")
//    @FilterAnnotation(url = "/user/updatetel", type = FilterType.login)
//    public ResultBean<String> updatetel(@RequestBody UpdatetelReq updatetelReq){
//        ResultBean<String> result = new ResultBean<>();
//        boolean flag = iUserService.updateUserTel(updatetelReq.getUsername(), updatetelReq.getTelephone());
//        if(flag){
//            result.setMsg("更新号码成功！更新为："+updatetelReq.getTelephone());
//        }
//        else{
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("更新号码失败！");
//        }
//        return result;
//    }
//
//    //修改权限
//    @CrossOrigin
//    @ApiOperation("修改权限")
//    @PostMapping("updateidentify")
//    @FilterAnnotation(url = "/user/updateidentify", type = FilterType.auth)
//    public ResultBean<String> updateidentify(@RequestBody UpdateidentifyReq updateidentifyReq) {
//        ResultBean<String> result = new ResultBean<>();
//        if(updateidentifyReq.getIdentify() < 0) {
//            result.setCode(ResultBean.FAIL);
//            result.setMsg("更新权限角色失败：更新权限等级有误");
//        }
//        else{
//            boolean flag = iUserService.updateUserIdentify(updateidentifyReq.getUsername(), updateidentifyReq.getIdentify());
//            if(flag){
//                result.setMsg("更新权限角色成功！更新为："+updateidentifyReq.getIdentify());
//            }
//            else{
//                result.setCode(ResultBean.FAIL);
//                result.setMsg("更新权限角色失败！");
//            }
//        }
//        return result;
//    }
//}

//package org.newhome.config;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
//import org.newhome.annotation.FilterAnnotation;
//import org.newhome.res.NormalRes;
//import org.newhome.util.ResultBean;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Component
//public class AuthInterceptor implements HandlerInterceptor {
//
//    @Value("${server.servlet.context-path}")
//    private String contextPath;
//
//
//    private final static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        if (handler instanceof ResourceHttpRequestHandler) {
//            return true;
//        }
//
//        String requestUrl = request.getRequestURI();
//        logger.info("requesturl:"+requestUrl);
//
//        String queryString = request.getQueryString();
//        logger.info(">>>>>收到请求:" + requestUrl + (StringUtils.isNotBlank(queryString) ? "?" + queryString : ""));
//        logger.info(">>>>>contextPaht:" + contextPath);
//
//        //去掉项目配置的contextPath
//        if (StringUtils.isNotBlank(contextPath)) {
//            requestUrl = requestUrl.replace(contextPath, "");
//        }
//
//        logger.info(">>>>>noc:" + requestUrl);
//        //开放一些需要开放的但又不能加入自定义注解的路径 比如 /doc.html
//        if (ArrayUtils.contains(Constant.ANNO_URLS, requestUrl)) {
//            return true;
//        }
//
//        //获取当前路由所对应的注解
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        FilterAnnotation filterAnnotation = handlerMethod.getMethod().getAnnotation(FilterAnnotation.class);
//
//        response.setCharacterEncoding("UTF-8");
//        request.setCharacterEncoding("UTF-8");
//        //如果没有注解  视为FilterType.anno 也就是不需要登录或其它操作 返回true代表通过，返回false代表拦截此请求
//        if (filterAnnotation == null) {
//            return true;
//        }
//
//        if (filterAnnotation.type().equals(FilterType.anno)) {
//            return true;
//        }
//
//        //没用到的几个
//        if (filterAnnotation.type().equals(FilterType.sta)) {
//            return true;
//        }
//
//        if (filterAnnotation.type().equals(FilterType.login)) {
//            //获取token
//            Cookie[] cookies = request.getCookies();
//            String tokenValue = null;
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("userTicket")) {
//                    tokenValue = cookie.getValue();
//                    break;
//                }
//            }
//            //没有token一律不准通过
//            if (StringUtils.isBlank(tokenValue)) {
//                returnJson(response, JSON.toJSONString(new NormalRes("NO_LOGIN",ResultBean.NO_LOGIN)));
//                return false;
//            }
//            //查看token是否过期
//            HttpSession httpSession = request.getSession();
//            UserInfo userInfo = (UserInfo)httpSession.getAttribute(tokenValue);
//            if (userInfo == null) {
//                returnJson(response, JSON.toJSONString(new NormalRes("NO_LOGIN",ResultBean.NO_LOGIN)));
//                return false;
//            }
//            return true;
//        }
//
//        if (filterAnnotation.type().equals(FilterType.regist)) {
//            return true;
//        }
//
//        //到了需要鉴权的时候了,这里只鉴定是否拥有开发者
//        if (filterAnnotation.type().equals(FilterType.auth)) {
//
//            //获取token
//            Cookie[] cookies = request.getCookies();
//            String tokenValue = null;
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("userTicket")) {
//                    tokenValue = cookie.getValue();
//                    break;
//                }
//            }
//            //没有token一律不准通过
//            if (StringUtils.isBlank(tokenValue)) {
//                returnJson(response, JSON.toJSONString(new NormalRes("NO_LOGIN",ResultBean.NO_LOGIN)));
//                return false;
//            }
//            //查看token是否过期
//            HttpSession httpSession = request.getSession();
//            UserInfo userInfo = (UserInfo)httpSession.getAttribute(tokenValue);
////            boolean isExpire = TokenUtil.INSTANCE.isExpire(tokenValue);
//            if (userInfo == null) {
//                returnJson(response, JSON.toJSONString(new NormalRes("NO_LOGIN",ResultBean.NO_LOGIN)));
//                return false;
//            }
//            //检测权限
//            boolean isHavePermission = userInfo.getAuthority() == -1;
//            if (!isHavePermission) {
//                returnJson(response, JSON.toJSONString(new NormalRes("NO_PERMISSION",ResultBean.NO_PERMISSION)));
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void returnJson (HttpServletResponse response, String json){
//
//            PrintWriter printWriter = null;
//            try {
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/html; charset=utf-8");
//                printWriter = response.getWriter();
//                printWriter.print(json);
//
//            } catch (IOException e) {
//                logger.error(">>>>>Authinterceptor response json error ", e);
//            } finally {
//                if (printWriter != null) {
//                    printWriter.close();
//                }
//            }
//
//        }
//}
//
//

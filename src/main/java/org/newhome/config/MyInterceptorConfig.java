package org.newhome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**").addResourceLocations("file:/home/py/images/");
//        registry.addResourceHandler("/3dmodel/**").addResourceLocations("file:/home/py/models/");
//        registry.addResourceHandler("/res/**").addResourceLocations("file:/home/wj/images/res/");

        registry.addResourceHandler("/img/**").addResourceLocations("file:"+Constant.ORI_IMAGE_PATH);
        registry.addResourceHandler("/3dmodel/**").addResourceLocations("file:"+Constant.MODEL_PATH);
        registry.addResourceHandler("/res/**").addResourceLocations("file:"+Constant.RESULT_PATH);
    }
}

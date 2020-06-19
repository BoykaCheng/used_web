package com.cque.usedweb.Configration;

import com.cque.usedweb.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenge on 2020.2.12 14:50
 */
@Configuration
public class MyConfigration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        //   "/*"只能拦截一层
        //   /* 是拦截所有的文件夹，不包含子文件夹
        //   /** 是拦截所有的文件夹及里面的子文件夹
        excludePath.add("/index");
        excludePath.add("/login");
        excludePath.add("/register");
        excludePath.add("/getAllVillages");
        excludePath.add("/permitLogin.html");
        excludePath.add("/notLogin");
        excludePath.add("/static/**");
        excludePath.add("/assets/**");
        excludePath.add("/bower_components/**");
        excludePath.add("/css/**");
        excludePath.add("/images/**");
        excludePath.add("/img/**");
        excludePath.add("/js/**");
        excludePath.add("/less/**");
        excludePath.add("/userImage/**");
        excludePath.add("/vendor/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludePath);
    }

    //已将文件保存到阿里OSS上，不用保存本地了
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/userImage/**").addResourceLocations("classpath:/static/userImage/");
        registry.addResourceHandler("/userImage/**").addResourceLocations("file:D:\\knowledge\\IDEA\\workspace\\download\\usedweb\\src\\main\\resources\\static\\userImage\\");
    }*/
}

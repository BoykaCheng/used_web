package com.cque.usedweb;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * springboot项目一般都有一个启动类来启动项目，而部署到tomcat不能通过该方式启动，所以需要改变启动方式
 * Created by Chenge on 2020.5.20 8:57
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向用main方法执行的Application启动类
        return builder.sources(UsedwebApplication.class);
    }
}

package cn.edu.nju.software.pinpoint.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//扫描 mybatis mapper 包路径
@MapperScan(basePackages = "cn.edu.nju.software.pinpoint.statistics.dao")
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"cn.edu.nju.software.pinpoint.statistics", "org.n3r.idworker"})
public class PinpointStatisticsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PinpointStatisticsApplication.class, args);
    }

    //为了打包springboot项目
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}



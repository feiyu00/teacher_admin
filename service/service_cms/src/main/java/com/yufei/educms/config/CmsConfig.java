package com.yufei.educms.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@MapperScan("com.yufei.educms.mapper")
@Configuration
public class CmsConfig {
    @Bean
    public ISqlInjector iSqlInjector() {
        return new LogicSqlInjector();
    }
    @Bean
    public OptimisticLockerInterceptor interceptor(){
        return new OptimisticLockerInterceptor();
    }
}

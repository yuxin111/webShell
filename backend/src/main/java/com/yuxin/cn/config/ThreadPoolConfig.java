package com.yuxin.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置
 * @Author YuXin
 * @Date 2021/8/22
 **/
@Configuration
public class ThreadPoolConfig {

    // 核心池大小
    private static final int CORE_POOL_SIZE = 5;
    // 最大线程数
    private static final int MAX_POOL_SIZE = 20;
    // 队列程度
    private static final int QUEUE_CAPACITY = 1000;
    // 线程空闲时间
    private static final int KEEPALIVE_SECONDS = 1000;

    @Bean("taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEPALIVE_SECONDS);
        return executor;
    }
}

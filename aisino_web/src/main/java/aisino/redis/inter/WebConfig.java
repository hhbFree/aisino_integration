package aisino.redis.inter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截所有请求
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    private RedisCacheInterceptor redisCacheInterceptor;



    /**
     * 配置拦截器
     *
     * @param registry
     * @author yuqingquan
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(this.redisCacheInterceptor).addPathPatterns("/**");
    }
}
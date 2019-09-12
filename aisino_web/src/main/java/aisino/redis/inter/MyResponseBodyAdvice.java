package aisino.redis.inter;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 数据回传到页面前处理数据存入redis
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //get请求
        if (returnType.hasMethodAnnotation(GetMapping.class)) {
            return true;
        }
        System.out.println( returnType.getExecutable().getName());
        if( returnType.getExecutable().getName().equals("userData")){
            return true;
        }
        return false;
    }

    /**
     *
     * @param body 返回的数据
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            String redisKey = RedisCacheInterceptor.createRedisKey(((ServletServerHttpRequest) request).getServletRequest());
            String redisValue;
            if(body instanceof String){
                redisValue = (String)body;
            }else{
                redisValue = mapper.writeValueAsString(body);
            }

            //没有命中就存入redis中
            Duration duration = Duration.ofMillis(1);
            long l = duration.toMillis();
            System.out.println(l);//过期时间
            this.redisTemplate.opsForValue().set(redisKey,redisValue,10, TimeUnit.SECONDS);
            //this.redisTemplate.expire(redisKey,10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}

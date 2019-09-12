package aisino.inter;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void save(){

//        for (int i = 0; i < 100; i++) {
//            this.redisTemplate.opsForValue().set("key_"+i,"value_"+i);
//        }
        Set<String> keys = this.redisTemplate.keys("key_*");
        for (String key : keys) {
             this.redisTemplate.delete(key);

        }
    }

    @Test
    public void get(){
            String s = this.redisTemplate.opsForValue().get("WEB_DATA_ad1f3e10c8a328d377089f877e593b5f");
            System.err.println(s);

    }

}

package org.tsymq.myredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class MyRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStringSet() throws Exception {
        redisTemplate.opsForValue().set("zc","tsymq");
    }

    @Test
    public void testStringGet() throws Exception {
        System.out.println(redisTemplate.opsForValue().get("zc"));
    }

}

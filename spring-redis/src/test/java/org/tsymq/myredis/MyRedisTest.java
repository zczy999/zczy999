package org.tsymq.myredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class MyRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStringSet() throws Exception {
        redisTemplate.opsForValue().set("zc","tsymq");
    }

    @Test
    public void testStringGet() throws Exception {
        Object zc = redisTemplate.opsForValue().get("zc");
        //使用了jackson序列化配置后获取string会多双引号
        String zc1 = stringRedisTemplate.opsForValue().get("zc");
        ObjectMapper objectMapper = new ObjectMapper();
        String valueWithoutQuotes = objectMapper.readValue(zc1, String.class);
        System.out.println((String)redisTemplate.opsForValue().get("zc"));
        System.out.println(stringRedisTemplate.opsForValue().get("zc"));
    }

}

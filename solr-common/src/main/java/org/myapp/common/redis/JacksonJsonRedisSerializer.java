package org.myapp.common.redis;


import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * User: wangweiwei
 * Date: 2/20/12
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("redisSerializer")
public class JacksonJsonRedisSerializer implements  RedisSerializer{
	
    private static ObjectMapper objectMapper =  new ObjectMapper();
    
    public String serialize(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public <T> T deserialize(String string, Class<T> cls) throws IOException {
        return objectMapper.readValue(string,cls);
    }
    
}


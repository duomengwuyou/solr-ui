package org.myapp.common.redis;

import java.io.IOException;


/**
 * User: wangweiwei
 * Date: 2/20/12
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */

public interface RedisSerializer {
    public String serialize(Object obj) throws IOException;
    public <T> T deserialize(String string, Class<T> cls) throws IOException;
}

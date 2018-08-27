package tmall.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("redisCacheUtil")
public class RedisCacheUtil {
    @Resource(name = "redisTemplate")
    private StringRedisTemplate redisTemplate;

    /*
     * 添加值    windows启动命令：命令窗口：D:\Redis-x64-3.2.100>redis-server.exe redis.windows.conf
     * */
    public void hSet(String key, String field, String value) {
        if (key == null || "".equals(key)) {
            return;
        }
        redisTemplate.opsForHash().put(key, field, value);
    }

    public String hGet(String key, String field) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    public boolean hExists(String key, String field) {
        if (key == null || "".equals(key)) {
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public void hDel(String key, String field) {
        if (key == null || "".equals(key)) {
            return;
        }
        redisTemplate.opsForHash().delete(key, field);
    }

    public long hSize(String key) {
        if (key == null || "".equals(key)) {
            return 0L;
        }
        return redisTemplate.opsForHash().size(key);
    }
}



package ci.smile.simswaporange.dao.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ci.smile.simswaporange.utils.dto.UserDto;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService { 

    @Autowired
    private RedisTemplate<String, UserDto> redisUserTemplate;

    public UserDto getUser(String key) {
        try {
            return redisUserTemplate.opsForValue().get(key);
        } catch (Exception e) {
            // Gérer l'exception
            return null;
        }
    }

    public boolean saveUser(String key, UserDto user, boolean isDelay) {
        try {
            if (isDelay) {
                redisUserTemplate.opsForValue().set(key, user, 20, TimeUnit.MINUTES);
            } else {
                redisUserTemplate.opsForValue().set(key, user);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<String> getKeys(String pattern) {
        try {
            return redisUserTemplate.keys(pattern + "*");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllKeys() {
        Set<byte[]> keys = redisUserTemplate.getConnectionFactory().getConnection().keys("*".getBytes());
        List<String> keysList = new ArrayList<>();
        Iterator<byte[]> it = keys.iterator();
        while (it.hasNext()) {
            byte[] data = (byte[]) it.next();
            keysList.add(new String(data, 0, data.length));
        }
        redisUserTemplate.getConnectionFactory().getConnection().close();
        return keysList;
    }

    public List<UserDto> getClusterUsers(String pattern) {
        try {
            return redisUserTemplate.opsForValue().multiGet(getKeys(pattern));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getTTL(String key) {
        try {
            return redisUserTemplate.getExpire(key);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteUser(String key) {
        redisUserTemplate.delete(key);
    }
}

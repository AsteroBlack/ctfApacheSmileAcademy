//package ci.smile.simswaporange.dao.config.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import ci.smile.simswaporange.utils.dto.UserDto;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class RedisDataInitialization {
//
//    @Autowired
//    private RedisTemplate<String, UserDto> redisUserTemplate;
//
//    @PostConstruct
//    public void initializeRedisData() {
//        UserDto user = new UserDto();
//        user.setId(1);
//        user.setLogin("john.doe");
//        user.setEmailAdresse("john.doe@example.com");
//
//        // Insertion des données dans Redis
//        redisUserTemplate.opsForValue().set("user:1", user, 20, TimeUnit.MINUTES);
//    }
//}

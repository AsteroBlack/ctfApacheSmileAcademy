package ci.smile.simswaporange.dao.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ci.smile.simswaporange.dao.entity.ActionUtilisateur;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDto;
import ci.smile.simswaporange.proxy.response.LockUnLockFreezeDtos;
import ci.smile.simswaporange.utils.dto.customize.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import ci.smile.simswaporange.utils.ParamsUtils;
import ci.smile.simswaporange.utils.dto.UserDto;
import redis.clients.jedis.JedisPoolConfig;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
@Slf4j
@Configuration
@RequiredArgsConstructor
@ComponentScan("ci.smile.simswaporange")
@EnableRedisRepositories(basePackages = "ci.smile.simswaporange")
//@PropertySource("classpath:application.properties")

public class RedisConfig {
	private final ParamsUtils paramsUtils;
	private final Environment environment;

	//@Autowired
	//private ParamsUtils paramsUtils;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		  JedisConnectionFactory factory = new JedisConnectionFactory();
		  log.info("redis host {}", paramsUtils.getRedisHost());
		  log.info("redis password {}", paramsUtils.getRedisPassword());
		  factory.setHostName(paramsUtils.getRedisHost());
		  factory.setPassword(paramsUtils.getRedisPassword());
		  factory.setPort(6379);
		  factory.setUsePool(true);
//		  JedisPoolConfig poolConfig = new JedisPoolConfig();
//		    poolConfig.setMaxIdle(30);
//		    poolConfig.setMinIdle(10);
//		    factory.setPoolConfig(poolConfig);
		return factory;
	}

	@Bean
	public RedisTemplate<String, UserDto> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, UserDto> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Bean
	public RedisTemplate<String, ActionUtilisateur> actionUtilisateurRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, ActionUtilisateur> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Primary
	@Bean
	public RedisTemplate<String, String> StringRedisTemplates(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Bean
	public RedisTemplate<String, Result> redisTemplateActionUtilisateur(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Result> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
	@Bean
	public StringRedisSerializer stringRedisSerializer() {
	  StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	  return stringRedisSerializer;
	}

	@Bean
	public RedisTemplate<String, LockUnLockFreezeDtos> redisTemplateLockunlockNumber(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, LockUnLockFreezeDtos> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		// Configurer le sérialiseur / désérialiseur si nécessaire
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, Long> redisTemplateTailleOfObject(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		// Configurer le sérialiseur / désérialiseur si nécessaire
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, LockUnLockFreezeDto> redisTemplateRefreshNumber(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, LockUnLockFreezeDto> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		// Configurer le sérialiseur / désérialiseur si nécessaire
		return redisTemplate;
	}

//
//	@Bean
//	public RedisCacheManager cacheManager() {
//	  RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
//	  redisCacheManager.setTransactionAware(true);
//	  redisCacheManager.setLoadRemoteCachesOnStartup(true);
//	  redisCacheManager.setUsePrefix(true);
//	  return redisCacheManager;
//	 }

//  @Bean
//  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//      RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//              .entryTtl(Duration.ofMinutes(10)); // Configuration par défaut avec une durée de vie de 10 minutes
//
//      Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
//      cacheConfigurations.put("itemCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));
//      cacheConfigurations.put("studentCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));
//
//      return RedisCacheManager.builder(redisConnectionFactory)
//              .cacheDefaults(defaultCacheConfig)
//              .withInitialCacheConfigurations(cacheConfigurations)
//              .build();
//  }
//  
//	@Bean 
//	public RedisTemplate<String, UserDto> redisTemplate() {
//		final RedisTemplate<String, UserDto> template = new RedisTemplate<String, UserDto>();
//		//template.setConnectionFactory(jedisConnectionFactory());
//		if (environment.getActiveProfiles() != null && environment.getActiveProfiles().length > 0) {
//			String activeProfiles = "";
//			for (int i = 0; i < environment.getActiveProfiles().length; i++) {
//				activeProfiles += environment.getActiveProfiles()[i];
//			}
//			slf4jLogger.info("active profile: " + activeProfiles);
//			if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equals("dev") || env.equals("staging"))) {
//				// DEV ou STAGING
//				template.setConnectionFactory(jedisConnectionFact());
//			}
//			if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equals("prod"))) {
//				// PROD
//				template.setConnectionFactory(jedisConnectionFactory());
//			}
//		}
//		template.setKeySerializer(new StringRedisSerializer());
//		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<UserDto>(UserDto.class));
//		template.setValueSerializer(new Jackson2JsonRedisSerializer<UserDto>(UserDto.class));
//		return template;
//  }
}


package com.upday.newsapi.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Jennifer Schulz
 */
@Configuration
public class RedisConfig {


    @Bean(name="redisTemplate")
    public StringRedisTemplate redisTemplate() throws Exception {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory cf = new JedisConnectionFactory();
        cf.setHostName("localhost");
        cf.setPort(6379);
        return cf;
    }

}

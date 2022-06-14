package com.example.translateapp.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisAccessor;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory());
//        return template;
//    }

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("127.0.0.1", 6379));
//    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return clientConfigurationBuilder -> {
            if(clientConfigurationBuilder.build().isUseSsl()) {
                clientConfigurationBuilder.useSsl().disablePeerVerification();
            }
        };
    }

//    @Bean
//    public static StatefulRedisConnection<String, String> connect() {
//        RedisURI redisURI = RedisURI.create(System.getenv("REDIS_URL"));
//        redisURI.setVerifyPeer(false);
//
//        return RedisClient.create().connect(redisURI);
//    }
}

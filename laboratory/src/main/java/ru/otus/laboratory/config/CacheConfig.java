package ru.otus.laboratory.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                        new ConcurrentMapCache("orderStatus"),
                        new ConcurrentMapCache("testStatus"),
                        new ConcurrentMapCache("testTubeStatus"),
                        new ConcurrentMapCache("testTubeError"),
                        new ConcurrentMapCache("testTubeItem")
                )
        );
        return cacheManager;
    }
}

package com.example.demo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(CorsProperties properties) {
        CorsConfiguration cfg = new CorsConfiguration();

        // 允许所有来源
        cfg.addAllowedOriginPattern("*");

        // 允许所有请求头
        cfg.addAllowedHeader("*");

        // 允许所有请求方法
        cfg.addAllowedMethod("*");

        // 允许携带凭证
        cfg.setAllowCredentials(true);

        // 预检请求的缓存时间（秒）
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return new CorsFilter(source);
    }
}

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

        if (!CollectionUtils.isEmpty(properties.getAllowedOrigins())) {
            properties.getAllowedOrigins().forEach(cfg::addAllowedOrigin);
        }
        if (!CollectionUtils.isEmpty(properties.getAllowedOriginPatterns())) {
            properties.getAllowedOriginPatterns().forEach(cfg::addAllowedOriginPattern);
        }

        // 如果都没配置，默认允许本地 5173 端口，方便开发
        if (CollectionUtils.isEmpty(cfg.getAllowedOrigins())
                && CollectionUtils.isEmpty(cfg.getAllowedOriginPatterns())) {
            cfg.addAllowedOrigin("http://localhost:5173");
        }

        if (!CollectionUtils.isEmpty(properties.getAllowedHeaders())) {
            properties.getAllowedHeaders().forEach(cfg::addAllowedHeader);
        } else {
            cfg.addAllowedHeader("*");
        }

        if (!CollectionUtils.isEmpty(properties.getAllowedMethods())) {
            properties.getAllowedMethods().forEach(cfg::addAllowedMethod);
        } else {
            cfg.addAllowedMethod("*");
        }

        cfg.setAllowCredentials(properties.isAllowCredentials());
        cfg.setMaxAge(properties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return new CorsFilter(source);
    }
}



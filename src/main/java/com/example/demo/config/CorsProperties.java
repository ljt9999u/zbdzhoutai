package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CORS 配置属性，来源于 application.yml
 */
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    /**
     * 精确匹配的允许来源，如 http://localhost:5173
     */
    private List<String> allowedOrigins = new ArrayList<>();

    /**
     * 使用通配符的来源，如 http://localhost:*
     */
    private List<String> allowedOriginPatterns = new ArrayList<>();

    private List<String> allowedMethods = new ArrayList<>(Collections.singletonList("*"));

    private List<String> allowedHeaders = new ArrayList<>(Collections.singletonList("*"));

    private long maxAge = 3600L;

    private boolean allowCredentials = true;

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedOriginPatterns() {
        return allowedOriginPatterns;
    }

    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }
}



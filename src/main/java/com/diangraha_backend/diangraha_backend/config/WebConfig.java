package com.diangraha_backend.diangraha_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // pastikan folder uploads berada di root project (atau sesuaikan)
        Path uploadDir = Paths.get("uploads");
        String location = uploadDir.toFile().getAbsolutePath() + "/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + location);
    }
}

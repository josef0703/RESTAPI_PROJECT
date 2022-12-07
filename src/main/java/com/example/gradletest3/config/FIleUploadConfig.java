package com.example.gradletest3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FIleUploadConfig {

    @Bean
        public CommonsMultipartResolver multipartResolver() {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
            multipartResolver.setDefaultEncoding("UTF-8");
            multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
            return multipartResolver;
        }
}

package com.example.helping_animals.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${photo.animal.directory.upload}")
    private String photoAnimalDirectoryUpload;

    @Value("${passport.animal.directory.upload}")
    private String passportUserDirectoryUpload;

    @Value("${photo.animal.directory.upload}")
    private String qrAnimalDirectoryUpload;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/animals/**")
                .addResourceLocations("file:/" + photoAnimalDirectoryUpload + "/");
        registry.addResourceHandler("/img/qr/**")
                .addResourceLocations("file:/" + qrAnimalDirectoryUpload + "/");
        registry.addResourceHandler("/img/passports/**")
                .addResourceLocations("file:/" + passportUserDirectoryUpload + "/");
    }
}
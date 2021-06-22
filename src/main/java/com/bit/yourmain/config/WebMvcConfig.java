package com.bit.yourmain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcesUriPath = "/profile/**";
        String resourcesLocation = "file:///C:/Users/User/Documents/profile/";

        String resourcesUriPathPost = "/postImage/**";
        String resourcesLocationPost = "file:///C:/Users/User/Documents/postImage/";

        registry.addResourceHandler(resourcesUriPath)
                .addResourceLocations(resourcesLocation);

        registry.addResourceHandler(resourcesUriPathPost)
                .addResourceLocations(resourcesLocationPost);

    }

}
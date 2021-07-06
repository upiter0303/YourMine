package com.bit.yourmain.config;

import com.bit.yourmain.config.interceptor.CustomInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final CustomInterceptor customInterceptor;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor)
                        .addPathPatterns("/chat/**")
                        .addPathPatterns("/posts/modify/*")
                        .addPathPatterns("/posts/delete/*")
                        .addPathPatterns("/review/**");
    }
}
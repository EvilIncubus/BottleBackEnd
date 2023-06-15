package org.bottleProject.configuration;

import org.bottleProject.controller.BottleController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final static Logger logger = Logger.getLogger(MvcConfig.class.getName());

    @Value("${resources.static-locations}")
    private String staticRes;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info(staticRes);
        registry
                .addResourceHandler("/photos/**")
                .addResourceLocations(staticRes);
    }
}
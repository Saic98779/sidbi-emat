package org.emat.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final EncryptedIdConverter encryptedIdConverter;
    private final EncryptedIdResolver encryptedIdResolver;

    public WebConfig(EncryptedIdConverter encryptedIdConverter, EncryptedIdResolver encryptedIdResolver) {
        this.encryptedIdConverter = encryptedIdConverter;
        this.encryptedIdResolver = encryptedIdResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(0, encryptedIdResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Long.class, encryptedIdConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:/home/ubuntu/uploads/");
    }
}

package com.shadath.overview.appConfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        try {
            messageSource.setBasenames("lang/messages");
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }
        finally {
            messageSource = null;
        }
    }
    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        try {
            interceptor.setParamName("lang");
            registry.addInterceptor(interceptor);
        }
        finally {
            registry = null;
        }
    }
}

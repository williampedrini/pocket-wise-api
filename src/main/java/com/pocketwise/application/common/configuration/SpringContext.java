package com.pocketwise.application.common.configuration;

import jakarta.annotation.Nonnull;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Simple static holder for the Spring {@link ApplicationContext} to allow
 * MapStruct expressions and other non-managed classes to obtain Spring beans
 * when constructor or field injection is not possible.
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@Nonnull final ApplicationContext applicationContext) throws BeansException {
        SpringContext.context = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        if (context == null) {
            throw new IllegalStateException("Spring application context is not initialized yet.");
        }
        return context.getBean(requiredType);
    }
}

package com.phunghv.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;
    private static final List<CallBack> CALL_BACKS = new ArrayList<>();
    private static boolean addCallback = true;

    public synchronized static void addCallBacks(CallBack callBack) {
        if (addCallback) {
            SpringContextHolder.CALL_BACKS.add(callBack);
        } else {
            log.warn("CallBack：{}", callBack.getCallBackName());
            callBack.executor();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getProperties(String property, T defaultValue, Class<T> requiredType) {
        T result = defaultValue;
        try {
            result = getBean(Environment.class).getProperty(property, requiredType);
        } catch (Exception ignored) {
        }
        return result;
    }

    public static String getProperties(String property) {
        return getProperties(property, null, String.class);
    }

    public static <T> T getProperties(String property, Class<T> requiredType) {
        return getProperties(property, null, requiredType);
    }

    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext SpringContextHolder.");
        }
    }

    private static void clearHolder() {
        applicationContext = null;
    }

    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext != null) {
            log.warn("application context: " + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
        if (addCallback) {
            for (CallBack callBack : SpringContextHolder.CALL_BACKS) {
                callBack.executor();
            }
            CALL_BACKS.clear();
        }
        SpringContextHolder.addCallback = false;
    }

    public static List<String> getAllServiceBeanName() {
        return new ArrayList<>(Arrays.asList(applicationContext
                .getBeanNamesForAnnotation(Service.class)));
    }
}

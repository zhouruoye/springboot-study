package com.cest.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static String[] getBeanNamesForType(Class<?> clazz ){
		return applicationContext.getBeanNamesForType(clazz);
	}

	public static <T> T getBean(String beanName,Class<T> clazz) {
		return applicationContext.getBean(beanName,clazz);
	}

	public static  <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

}

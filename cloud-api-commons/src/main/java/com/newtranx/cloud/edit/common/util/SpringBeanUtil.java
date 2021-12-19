//package com.newtranx.cloud.edit.common.util;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SpringBeanUtil implements ApplicationContextAware{
//
//	public static ApplicationContext context;
//
//	public static Object getBeanByName(String beanName) {
//		return context.getBean(beanName);
//	}
//
//	public static <T> T getBean(Class<T> type) {
//		return context.getBean(type);
//	}
//
//	public static ApplicationContext getContext() {
//		return context;
//	}
//
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		SpringBeanUtil.context = applicationContext;
//	}
//}
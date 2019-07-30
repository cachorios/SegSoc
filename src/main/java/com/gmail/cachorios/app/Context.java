package com.gmail.cachorios.app;

import org.springframework.beans.BeansException;

public class Context {
	
	public static  <T> T  getBean(Class<T> beanName) throws BeansException{
		return 	ApplicationContextProvider.getApplicationContext().getBeanNamesForType(beanName).length == 1 ?
				ApplicationContextProvider.getApplicationContext().getBean(beanName) :
				ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory().createBean(beanName);
	}
}

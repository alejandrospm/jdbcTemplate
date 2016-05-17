package com.jeincrementer.dao;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Profile("dev")
public class CustomBeaPostProcessor implements BeanPostProcessor {

	
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Before Bean ''" + "created : " + bean.getClass().getSimpleName());
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("After Bean ''" + "created : " + bean.getClass().getSimpleName());
		return bean;
	}

}
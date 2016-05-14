package com.jeincrementer.dao;

import java.io.Serializable;

public interface GenericDao<T extends Serializable> {
	
	public T create(T object);
	
	public T update(T object);

}

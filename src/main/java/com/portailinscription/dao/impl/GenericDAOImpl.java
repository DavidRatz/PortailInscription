package com.portailinscription.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDAOImpl<T>{

	@PersistenceContext
    protected EntityManager em;
	
	private Class<T> type;
	
	public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
	
	public void insert(T t) {
		em.persist(t);
	}

	public void update(T t) {
		em.merge(t);
	}

	public void delete(Object id) {
		em.remove(em.getReference(type, id));	
	}

	public T find(Object id) {
		return this.em.find(type, id);
	}

}

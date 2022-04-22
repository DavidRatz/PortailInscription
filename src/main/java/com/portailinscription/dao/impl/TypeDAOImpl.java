package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Type;

@Repository("typeDao")
public class TypeDAOImpl extends GenericDAOImpl<Type>{
	
	@PersistenceContext
    private EntityManager manager;

	static Logger log = Logger.getLogger(TypeDAOImpl.class.getName());
	
	public List<String> getAllTypes()
	{
		return manager.createQuery("Select t.nom From Type t").getResultList();
	}
	
	public Type getTypeByName(String nom) {
		TypedQuery<Type> query = manager.createQuery("Select t From Type t Where t.nom = :nom", Type.class);
		query.setParameter("nom", nom);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			log.info(e);
			throw new NoResultException(e.getMessage());
		}
	}
}

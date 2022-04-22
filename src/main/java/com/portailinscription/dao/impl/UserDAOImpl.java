package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.User;

@Repository("userDao")
public class UserDAOImpl extends GenericDAOImpl<User> {
	
	@PersistenceContext
    private EntityManager manager;

	static Logger log = Logger.getLogger(UserDAOImpl.class.getName());
	
	public User findUserByPassword(String password) {
		TypedQuery<User> query = manager.createQuery("Select u From User u Join u.role r Where u.motPasse = :motdepasse",User.class);
		query.setParameter("motdepasse", password);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			log.info(e);
			throw new NoResultException(e.getMessage());
		}
	}
	
	public User findUserByUsername(String email) {
		TypedQuery<User> query = manager.createQuery("Select u From User u Join u.role r Where u.id = (Select e.utilisateur From Entreprise e Where e.emailContact = :email)",User.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			log.info(e);
			return null;
		}
	}

	public List<User> getAllUsers() {
		return manager.createQuery("Select u From User u", User.class).getResultList();
	}
	
	public User getUserByEntreprise(int idEntreprise)
	{
		TypedQuery<User> query = manager.createQuery("Select u From User u Where u.id = (select e.utilisateur from Entreprise e where e.id = :id)", User.class);
		try {
			query.setParameter("id", idEntreprise);
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			log.info(e);
			throw new NoResultException(e.getMessage());
		}
	}
}

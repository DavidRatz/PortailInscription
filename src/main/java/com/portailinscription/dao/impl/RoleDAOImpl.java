package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Role;

@Repository("roleDao")
public class RoleDAOImpl extends GenericDAOImpl<Role>{
	
	@PersistenceContext
    private EntityManager manager;
	
	static Logger log = Logger.getLogger(RoleDAOImpl.class.getName());
	
	public Role findRoleByName(String nom){
		TypedQuery<Role> query = manager.createQuery("Select r From Role r Where r.nom = :nom", Role.class);
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
	
	public List<Role> getAllRoles() {
		return manager.createQuery("Select r From Role r", Role.class).getResultList();
	}
	
	public List<Role> findRolesByIdUtilisateur(int idUtilisateur)
	{
		TypedQuery<Role> query = manager.createQuery("Select r From User u Join u.role r Where u.id = :idUtilisateur", Role.class);
		query.setParameter("idUtilisateur", idUtilisateur);
		return query.getResultList();
	}
}


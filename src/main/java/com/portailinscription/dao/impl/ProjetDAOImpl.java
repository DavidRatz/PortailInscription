package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Projet;

@Repository("projetDao")
public class ProjetDAOImpl extends GenericDAOImpl<Projet>{
	
	@PersistenceContext
    private EntityManager manager;
	
	static Logger log = Logger.getLogger(ProjetDAOImpl.class.getName());
	
	public Projet findProjetByName(String nom){
		TypedQuery<Projet> query = manager.createQuery("Select p From Projet p Where p.nom = :nom", Projet.class);
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
	
	public List<Projet> getAllProjetsByEntreprise(int id){
		TypedQuery<Projet> query = manager.createQuery("Select p From Projet p Join p.entreprise e Where e.id = :id", Projet.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Projet> getAllProjets() {
		return manager.createQuery("Select p From Projet p Join p.entreprise", Projet.class).getResultList();
	}
	
	public List<String> getAllNameOfProjets() {
		return manager.createQuery("Select p.nom From Projet p").getResultList();
	}
}

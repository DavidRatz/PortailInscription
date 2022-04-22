package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Entreprise;

@Repository("entrepriseDao")
public class EntrepriseDAOImpl extends GenericDAOImpl<Entreprise>{

	@PersistenceContext
    private EntityManager manager;
	
	static Logger log = Logger.getLogger(EntrepriseDAOImpl.class.getName());
	
	public Entreprise findEntrepriseByName(String nom) {
		TypedQuery<Entreprise> query = manager.createQuery("Select e From Entreprise e Where e.nom = :nom", Entreprise.class);
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
	
	public List<Entreprise> getAllEntreprises() {
		return manager.createQuery("Select e From Entreprise e", Entreprise.class).getResultList();
	}
	
	public Entreprise getEntrepriseByEmailAndPassword(String email, String password) {
		TypedQuery<Entreprise> query = manager.createQuery("Select e From Entreprise e Join e.utilisateur u where e.emailContact = :email And u.motPasse = :motdepasse", Entreprise.class);
		query.setParameter("email", email);
		query.setParameter("motdepasse", password);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			log.info(e);
			return null;
		}
	}
	
	public Entreprise getEntrepriseByEmail(String email) {
		TypedQuery<Entreprise> query = manager.createQuery("Select e From Entreprise e where e.emailContact = :email", Entreprise.class);
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
	
	public List<Entreprise> getEntrepriseSousTraitanteByType(String type, int idEntrepriseMaitre) {
		TypedQuery<Entreprise> query = manager.createQuery("Select e From Entreprise e where e.numeroBceMaitre = (Select e2.numeroBCE From Entreprise e2 where e2.type = :type And e2.id = :id)", Entreprise.class);
		query.setParameter("type", type);
		query.setParameter("id", idEntrepriseMaitre);
		return query.getResultList();
	}
	
	public Entreprise findEntrepriseByTravailleur(int id) {
		TypedQuery<Entreprise> query = manager.createQuery("Select e From Entreprise e Where e.id = (Select t.entreprise From Travailleur t where t.id = :id)", Entreprise.class);
		query.setParameter("id", id);
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

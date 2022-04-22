package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Acces;

@Repository("accesDao")
public class AccesDAOImpl extends GenericDAOImpl<Acces>{
	
	@PersistenceContext
    private EntityManager manager;
	
	static Logger log = Logger.getLogger(AccesDAOImpl.class.getName());

	public List<Acces> getAllAcces() {
		return manager.createQuery("Select a From Acces a", Acces.class).getResultList();
	}
	
	public List<Acces> getAccesByTravailleur(int idTravailleur){
		TypedQuery<Acces> query = manager.createQuery("Select a From Acces a Join a.travailleur t Where t.id = :id ", Acces.class);
		query.setParameter("id", idTravailleur);
		return query.getResultList();
	}
	
	public List<Acces> getAccesByEntreprise(int idEntreprise){
		TypedQuery<Acces> query = manager.createQuery("Select a From Acces a Join a.travailleur t Join t.entreprise e Where e.id = :id ", Acces.class);
		query.setParameter("id", idEntreprise);
		return query.getResultList();
	}
}

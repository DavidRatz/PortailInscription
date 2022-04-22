package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Travailleur;

@Repository("travailleurDao")
public class TravailleurDAOImpl extends GenericDAOImpl<Travailleur>{
	
	@PersistenceContext
    private EntityManager manager;
	
	static Logger log = Logger.getLogger(TravailleurDAOImpl.class.getName());
	
	public List<Travailleur> getAllTravailleursByEntreprise(int idEntreprise)
	{
		TypedQuery<Travailleur> listeTravailleurs = manager.createQuery("Select t From Travailleur t Join t.entreprise e Where e.id = :id", Travailleur.class);
		listeTravailleurs.setParameter("id", idEntreprise);
		return listeTravailleurs.getResultList();
	}
	
	public List<Travailleur> getAllTravailleursByTypeEntreprise(String type, int idEntrepriseMaitre)
	{
		TypedQuery<Travailleur> listeTravailleurs = manager.createQuery("Select t From Travailleur t Join t.entreprise e Where e.numeroBceMaitre = (Select e2.numeroBCE From Entreprise e2 Where e2.type = 'Societe sous-traitante maitre' And e2.id = :id) And e.type = :type", Travailleur.class);
		listeTravailleurs.setParameter("id", idEntrepriseMaitre);
		listeTravailleurs.setParameter("type", type);
		return listeTravailleurs.getResultList();
	}
	
	public List<Travailleur> getAllTravailleurs()
	{
		return manager.createQuery("Select t From Travailleur t", Travailleur.class).getResultList();
	}
	
	public List<String> getAllNameOfTravailleurs() {
		return manager.createQuery("Select Concat(t.nom, ' ', t.prenom) From Travailleur t").getResultList();
	}
	
	public Travailleur getTravailleurByName(String nom){
		TypedQuery<Travailleur> query = manager.createQuery("Select t From Travailleur t Where Concat(t.nom, ' ', t.prenom) = :nom", Travailleur.class);
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
	
	public List<Travailleur> getAllTravailleursWithAcces()
	{
		return manager.createQuery("Select t From Travailleur t Join t.acces a where a.travailleur = t.id", Travailleur.class).getResultList();
	}
}

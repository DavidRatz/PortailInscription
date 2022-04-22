package com.portailinscription.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.portailinscription.model.Document;

@Repository("documentDao")
public class DocumentDAOImpl extends GenericDAOImpl<Document>{

	@PersistenceContext
    private EntityManager manager;
	
	static Logger log = Logger.getLogger(DocumentDAOImpl.class.getName());
	
	public Document findDocumentByNomFichier(String nomFichier) {
		TypedQuery<Document> query = manager.createQuery("Select d From Document d Where d.nomFichier = :nom", Document.class);
		query.setParameter("nom", nomFichier);
		try {
			return query.getSingleResult();
		}
		catch(NoResultException e)
		{
			log.info(e);
			throw new NoResultException(e.getMessage());
		}
	}
	
	public List<Document> getAllDocuments() {
		return manager.createQuery("Select d From Document d", Document.class).getResultList();
	}
	
	public List<Document> getDocumentByEntreprise(int idEntreprise)
	{
		TypedQuery<Document> query = manager.createQuery("Select d From Document d Join d.entreprise e Where e.id = :id", Document.class);
		query.setParameter("id", idEntreprise);
		return query.getResultList();
	}
	
	public List<Document> getDocumentByTravailleur(int idTravailleur)
	{
		TypedQuery<Document> query = manager.createQuery("Select d From Document d Join d.travailleur t Where t.id = :id", Document.class);
		query.setParameter("id", idTravailleur);
		return query.getResultList();
	}
	
	public Document getDocumentByEntrepriseAndName(int idEntreprise, String nom)
	{
		TypedQuery<Document> query = manager.createQuery("Select d From Document d Join d.entreprise e Where e.id = :id And d.nom = :nom", Document.class);
		query.setParameter("id", idEntreprise);
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
	
	public Document getDocumentByTravailleurAndName(int idTravailleur, String nom)
	{
		TypedQuery<Document> query = manager.createQuery("Select d From Document d Join d.travailleur e Where e.id = :id And d.nom = :nom", Document.class);
		query.setParameter("id", idTravailleur);
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

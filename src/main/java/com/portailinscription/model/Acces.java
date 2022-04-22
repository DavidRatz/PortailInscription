package com.portailinscription.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "acces")
public class Acces implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	@Temporal(TemporalType.DATE)
	private Date dateValidation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	@Column
	private String etatDemande;
	@ManyToOne
	@JoinColumn(name="id_projet")
	private Projet projet;
	@ManyToOne
	@JoinColumn(name="id_travailleur")
	private Travailleur travailleur;
	
	public Acces() {
		super();
	}
	
	public Acces(Date dateDebut, Date dateFin, Date dateCreation, Date dateValidation, String etatDemande) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dateCreation = dateCreation;
		this.dateValidation = dateValidation;
		this.etatDemande = etatDemande;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Date getDateValidation() {
		return dateValidation;
	}
	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getEtatDemande() {
		return etatDemande;
	}
	public void setEtatDemande(String etatDemande) {
		this.etatDemande = etatDemande;
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public Travailleur getTravailleur() {
		return travailleur;
	}
	public void setTravailleur(Travailleur travailleur) {
		this.travailleur = travailleur;
	}
}

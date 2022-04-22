package com.portailinscription.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "document")
public class Document implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private String nom;
	@Column
	private String nomFichier;
	@Temporal(TemporalType.DATE)
	private Date dateValidite;
	@ManyToOne
	@JoinColumn(name="id_travailleur")
	private Travailleur travailleur;
	@ManyToOne
	@JoinColumn(name="id_entreprise")
	private Entreprise entreprise;
	
	public Document() {
		super();
	}
	public Document(int id, String nom, String nomFichier, Date dateValidite, Travailleur travailleur,
			Entreprise entreprise) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomFichier = nomFichier;
		this.dateValidite = dateValidite;
		this.travailleur = travailleur;
		this.entreprise = entreprise;
	}
	public Document(String nom, String nomFichier, Date dateValidite) {
		super();
		this.nom = nom;
		this.nomFichier = nomFichier;
		this.dateValidite = dateValidite;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNomFichier() {
		return nomFichier;
	}
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	public Date getDateValidite() {
		return dateValidite;
	}
	public void setDateValidite(Date dateValidite) {
		this.dateValidite = dateValidite;
	}
	public Travailleur getTravailleur() {
		return travailleur;
	}
	public void setTravailleur(Travailleur travailleur) {
		this.travailleur = travailleur;
	}
	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	
}

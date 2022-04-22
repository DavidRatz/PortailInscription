package com.portailinscription.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "travailleur")
public class Travailleur implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private int numeroReference;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String nom;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String prenom;
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	@Column
	private String photo;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String nationalite;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String langue;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String telephone;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String adresseEmail;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModification;
	@ManyToOne
	@JoinColumn(name="id_entreprise")
	private Entreprise entreprise;
	@ManyToOne
	@JoinColumn(name="id_listenoire")
	private ListeNoire listeNoire;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="travailleur")
	private List<Acces> acces;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="travailleur")
	private List<Document> documents;
	
	public Travailleur() {
		super();
	}
	
	
	public Travailleur(int numeroReference,  String nom, String prenom, Date dateNaissance, String photo,
			 String nationalite,  String langue, String telephone,  String adresseEmail, Date dateCreation,
			Date dateModification, Entreprise entreprise, ListeNoire listeNoire, List<Acces> acces,	List<Document> documents) {
		super();
		this.numeroReference = numeroReference;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.photo = photo;
		this.nationalite = nationalite;
		this.langue = langue;
		this.telephone = telephone;
		this.adresseEmail = adresseEmail;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
		this.entreprise = entreprise;
		this.listeNoire = listeNoire;
		this.acces = acces;
		this.documents = documents;
	}

	public Travailleur(Travailleur travailleur){
		this(travailleur.getNumeroReference(), travailleur.getNom(), travailleur.getPrenom(), travailleur.getNationalite(),
				travailleur.getLangue(), travailleur.getTelephone(), travailleur.getAdresseEmail());
	}
	
	public Travailleur(int numeroReference, String nom, String prenom, String nationalite, 
			String langue, String telephone, String adresseEmail) {
		super();
		this.numeroReference = numeroReference;
		this.nom = nom;
		this.prenom = prenom;
		this.nationalite = nationalite;
		this.langue = langue;
		this.telephone = telephone;
		this.adresseEmail = adresseEmail;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumeroReference() {
		return numeroReference;
	}
	public void setNumeroReference(int numeroReference) {
		this.numeroReference = numeroReference;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	public String getLangue() {
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAdresseEmail() {
		return adresseEmail;
	}
	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	public ListeNoire getListeNoire() {
		return listeNoire;
	}
	public void setListeNoire(ListeNoire listeNoire) {
		this.listeNoire = listeNoire;
	}
	public List<Acces> getAcces() {
		return acces;
	}
	public void setAcces(List<Acces> acces) {
		this.acces = acces;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateModification() {
		return dateModification;
	}
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}
	@Override
	public String toString() {
		return "Travailleur [nom=" + nom + ", prenom=" + prenom + "]";
	}
	
}

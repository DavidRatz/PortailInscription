package com.portailinscription.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.portailinscription.model.User;

@Entity
@Table(name = "entreprise")
public class Entreprise implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	@NotNull
	@Pattern(regexp="([A-Z]{2})([0-9]{9})", message="Le numéro BCE doit contenir le préfix du pays suivi de 9 chiffres")
	private String numeroBCE;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String nom;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String nomContact;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String prenomContact;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String telContact;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String emailContact;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String rue;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String numero;
	@Column
	private int codePostal;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String localite;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String pays;
	@Column
	private String etat;
	@Column
	@NotNull 
	@Size(min=2, max=255, message="Aucun type n'est sélectionné !")
	private String type;
	@Column
	@NotNull 
	@Size(min=2, max=255)
	private String raisonDemande;
	@Column
	@NotNull 
	@Size(min=2, max=255)
	private String numeroReference;
	@Column
	private String remarque;
	@Column
	private String numeroBceMaitre;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModification;
	@ManyToOne
	@JoinColumn(name="id_utilisateur")
	private User utilisateur;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entreprise")
	private List<Travailleur> travailleurs;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entreprise")
	private List<Document> documents;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entreprise")
	private List<Projet> projets;
	
	public Entreprise() {
		super();
	}

	public Entreprise(String numeroBCE, String nom, String nomContact, String prenomContact, String telContact, String emailContact, 
			String rue, String numero, int codePostal, String localite, String pays, String etat, String type, String raisonDemande, String numeroReference, String remarque, String numeroBceMaitre) {
		super();
		this.numeroBCE = numeroBCE;
		this.nom = nom;
		this.nomContact = nomContact;
		this.prenomContact = prenomContact;
		this.telContact = telContact;
		this.emailContact = emailContact;
		this.rue = rue;
		this.numero = numero;
		this.codePostal = codePostal;
		this.localite = localite;
		this.pays = pays;
		this.etat = etat;
		this.type = type;
		this.raisonDemande = raisonDemande;
		this.numeroReference = numeroReference;
		this.remarque = remarque;
		this.numeroBceMaitre = numeroBceMaitre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroBCE() {
		return numeroBCE;
	}

	public void setNumeroBCE(String numeroBCE) {
		this.numeroBCE = numeroBCE;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomContact() {
		return nomContact;
	}

	public void setNomContact(String nomContact) {
		this.nomContact = nomContact;
	}

	public String getPrenomContact() {
		return prenomContact;
	}

	public void setPrenomContact(String prenomContact) {
		this.prenomContact = prenomContact;
	}

	public String getTelContact() {
		return telContact;
	}

	public void setTelContact(String telContact) {
		this.telContact = telContact;
	}

	public String getEmailContact() {
		return emailContact;
	}

	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getLocalite() {
		return localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRaisonDemande() {
		return raisonDemande;
	}

	public void setRaisonDemande(String raisonDemande) {
		this.raisonDemande = raisonDemande;
	}

	public String getNumeroReference() {
		return numeroReference;
	}

	public void setNumeroReference(String numeroReference) {
		this.numeroReference = numeroReference;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public String getNumeroBceMaitre() {
		return numeroBceMaitre;
	}

	public void setNumeroBceMaitre(String numeroBceMaitre) {
		this.numeroBceMaitre = numeroBceMaitre;
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
	
	public User getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(User utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Travailleur> getTravailleurs() {
		return travailleurs;
	}

	public void setTravailleurs(List<Travailleur> travailleurs) {
		this.travailleurs = travailleurs;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<Projet> getProjets() {
		return projets;
	}

	public void setProjets(List<Projet> projets) {
		this.projets = projets;
	}
	
	public void add(Travailleur travailleur)
	{
		travailleurs.add(travailleur);
	}
}

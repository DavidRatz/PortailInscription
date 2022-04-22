package com.portailinscription.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.portailinscription.model.Entreprise;

@Entity
@Table(name = "projet")
public class Projet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	@NotNull
	@Size(min=2, max=255)
	private String nom;
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
	@ManyToOne
	@JoinColumn(name="id_entreprise")
	private Entreprise entreprise;
	@ManyToOne
	@JoinColumn(name="id_type")
	private Type type;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="projet")
	private List<Acces> acces;
	
	public Projet() {
		super();
	}
	
	public Projet(String nom, String rue, String numero, int codePostal, String localite, String pays) {
		super();
		this.nom = nom;
		this.rue = rue;
		this.numero = numero;
		this.codePostal = codePostal;
		this.localite = localite;
		this.pays = pays;
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

	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Acces> getAcces() {
		return acces;
	}

	public void setAcces(List<Acces> acces) {
		this.acces = acces;
	}
}

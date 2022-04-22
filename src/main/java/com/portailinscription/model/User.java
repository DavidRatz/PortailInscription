package com.portailinscription.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "utilisateur")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private String motPasse;
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;
	private boolean enabled;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="utilisateur")
	private List<Entreprise> entreprises;

	public User() {
		super();
	}

	public User(int id, String motPasse) {
		this.id = id;
		this.motPasse = motPasse;
	}
	
	public User(String motPasse) {
		this.motPasse = motPasse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMotPasse() {
		return motPasse;
	}

	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Entreprise> getEntreprises() {
		return entreprises;
	}

	public void setEntreprises(List<Entreprise> entreprises) {
		this.entreprises = entreprises;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	
}

package com.portailinscription.form;

import javax.validation.constraints.*;

public class EntrepriseModificationForm {
	@NotNull
	@Size(min=2, max=255, message="Il n'y a pas de fichier sélectionné !")
	private String bonCommande;
	@NotNull
	@Size(min=2, max=255, message="Il n'y a pas de fichier sélectionné !")
	private String statutSociete;
	private String email;
	
	public String getBonCommande() {
		return bonCommande;
	}
	public void setBonCommande(String bonCommande) {
		this.bonCommande = bonCommande;
	}
	public String getStatutSociete() {
		return statutSociete;
	}
	public void setStatutSociete(String statutSociete) {
		this.statutSociete = statutSociete;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

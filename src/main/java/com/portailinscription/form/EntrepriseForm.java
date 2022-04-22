package com.portailinscription.form;

import javax.validation.constraints.*;

public class EntrepriseForm {
	@NotNull
	@Size(min=2, max=255, message="Il n'y a pas de fichier sélectionné !")
	private String bonCommande;
	@NotNull
	@Size(min=2, max=255, message="Il n'y a pas de fichier sélectionné !")
	private String statutSociete;
	@NotNull
	@Size(min=6, max=255)
	private String motPasse;
	@NotNull
	@Size(min=6, max=255)
	private String confirmation;
	
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
	public String getMotPasse() {
		return motPasse;
	}
	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
}

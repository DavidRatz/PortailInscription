package com.portailinscription.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangeMotPasseForm {
	@NotNull
	@Size(min=6, max=255)
	private String motDePasseActuel;
	@NotNull
	@Size(min=6, max=255)
	private String nouveauMotDePasse;
	@NotNull
	@Size(min=6, max=255)
	private String confirmationMotDePasse;
	
	public String getMotDePasseActuel() {
		return motDePasseActuel;
	}
	public void setMotDePasseActuel(String motDePasseActuel) {
		this.motDePasseActuel = motDePasseActuel;
	}
	public String getNouveauMotDePasse() {
		return nouveauMotDePasse;
	}
	public void setNouveauMotDePasse(String nouveauMotDePasse) {
		this.nouveauMotDePasse = nouveauMotDePasse;
	}
	public String getConfirmationMotDePasse() {
		return confirmationMotDePasse;
	}
	public void setConfirmationMotDePasse(String confirmationMotDePasse) {
		this.confirmationMotDePasse = confirmationMotDePasse;
	}
}

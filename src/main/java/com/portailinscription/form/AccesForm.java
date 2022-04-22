package com.portailinscription.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccesForm {
	
	private String nomProjet;
	private String nomTravailleur;
	@NotNull(message = "Veuillez indiquer une date de début !")
	private String dateDebut;
	@NotNull(message = "Veuillez indiquer une date de fin !")
	private String dateFin;
	
	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public String getNomTravailleur() {
		return nomTravailleur;
	}
	public void setNomTravailleur(String nomTravailleur) {
		this.nomTravailleur = nomTravailleur;
	}
	public String getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
}

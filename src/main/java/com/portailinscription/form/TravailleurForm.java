package com.portailinscription.form;

import javax.validation.constraints.*;

import org.springframework.web.multipart.MultipartFile;

public class TravailleurForm {
	private MultipartFile photoFichier;
	private MultipartFile[] file;
	@NotNull(message = "Veuillez indiquer une date  !")
	private String[] date;
	@NotNull(message = "Veuillez indiquer une date de naissance !")
	private String dateNaissance;
	
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public MultipartFile getPhotoFichier() {
		return photoFichier;
	}
	public void setPhotoFichier(MultipartFile photoFichier) {
		this.photoFichier = photoFichier;
	}
	public MultipartFile[] getFile() {
		return file;
	}
	public void setFile(MultipartFile[] file) {
		this.file = file;
	}
	public String[] getDate() {
		return date;
	}
	public void setDate(String[] date) {
		this.date = date;
	}
}

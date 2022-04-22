package com.portailinscription.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConnectForm {
	@NotNull
	private String username;
	@NotNull
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/*@NotNull
	@Size(min=2, max=255)
	private String email;
	@NotNull
	@Size(min=6, max=255)
	private String motPasse;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMotPasse() {
		return motPasse;
	}

	public void setMotPasse(String motPasse) {
		this.motPasse = motPasse;
	}*/
}

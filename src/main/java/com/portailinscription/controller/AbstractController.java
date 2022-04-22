package com.portailinscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.portailinscription.dao.impl.EntrepriseDAOImpl;
import com.portailinscription.model.Entreprise;

@Controller
public class AbstractController {

	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	
	protected Entreprise getLoggedEntreprise(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Entreprise entreprise = new Entreprise();
		if(auth != null){
			String emailEntreprise = SecurityContextHolder.getContext().getAuthentication().getName();
			entreprise = entrepriseDao.getEntrepriseByEmail(emailEntreprise);
		}	
		return entreprise;
	}
	
	protected boolean estConnecte(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken) ){
			return true;
		}
		else{
			return false;
		}
	}
}

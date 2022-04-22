package com.portailinscription.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccueilController{
		
	/**
	 * Afficher la page d'accueil
	 * 
	 * @param model 
	 * @return /index page
	 */

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) { 
		return "index";
	}
	
	/**
	 * Afficher la page d'accueil
	 * 
	 * @param model 
	 * @return /index page
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() { 
		return "index";
	}
}

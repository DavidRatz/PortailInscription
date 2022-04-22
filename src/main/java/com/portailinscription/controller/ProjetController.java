package com.portailinscription.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.portailinscription.form.*;
import com.portailinscription.model.*;
import com.portailinscription.dao.impl.*;

@Controller
@RequestMapping(value = "/projet")
public class ProjetController extends AbstractController{
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private ProjetDAOImpl projetDao;
	@Autowired
	private TypeDAOImpl typeDao;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	private Projet projet;
	private Entreprise entreprise;
	private ProjetForm projetForm;
	private List<String> types;
	private List<String> listPays;
	private String nomEntreprise;
	private HttpSession httpSession;
	
	private static final String GESTIONPROJET = "projet";
	private static final String LISTETYPES = "types";
	private static final String LISTEPAYS = "listPays";
	private static final String REDIRECTLISTEPROJET = "redirect:listeProjet.html";
	
	@PostConstruct
	public void init() {
		projet = new Projet();
		projetForm = new ProjetForm();
		listPays = new ArrayList<>();
		
		listPays.add("Allemagne");
		listPays.add("Belgique");
		listPays.add("Espagne");
		listPays.add("France");
		listPays.add("Luxembourg");
		listPays.add("Pays-bas");
		listPays.add("Suisse");
	}
	
	/**
	 *  @return /ajouterProjet page form pour ajouter un projet pour une entreprise
	 */
	@RequestMapping(value = "/ajouterProjet", method = RequestMethod.GET)
	public String add(Model model) {		
		types = typeDao.getAllTypes();
	    model.addAttribute(GESTIONPROJET, projet);
	    model.addAttribute("projetForm", new ProjetForm());
	    model.addAttribute(LISTETYPES, types);
	    model.addAttribute(LISTEPAYS, listPays);
	    
		return "projet/ajouterProjet";
	}
	
	/**
	 * Ajouter un projet
	 * 
	 * @param projet
	 * @param projetForm
	 * @param bindingResult
	 * @param model
	 * @return Redirect to /index page to display project list
	 */
	@RequestMapping(value = "/ajouterProjet", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String add(@Valid @ModelAttribute("projet") Projet projet, BindingResult bindingResult, @Valid @ModelAttribute("projetForm") ProjetForm projetForm, BindingResult bindingResultForm, Model model) {
		
		if(bindingResult.hasErrors() || bindingResultForm.hasErrors())
		{
			types = typeDao.getAllTypes();
			model.addAttribute(LISTETYPES, types);
			model.addAttribute(LISTEPAYS, listPays);
			
			return "projet/ajouterProjet";
		}
		else
		{
			httpSession = httpServletRequest.getSession(true);
			nomEntreprise = (String)httpSession.getAttribute("nomEntreprise");
			entreprise = entrepriseDao.findEntrepriseByName(nomEntreprise);
			projet.setEntreprise(entreprise);
			Type type = typeDao.getTypeByName(projetForm.getNomType());
			projet.setType(type);
			projetDao.insert(projet);
			
			return REDIRECTLISTEPROJET;
		}
	}
	
	/**
	 *  @return /updateProject page form to add project
	 */
	@RequestMapping(value = "/modifierProjet", method = RequestMethod.GET)
	public String update(@RequestParam(name = "id", required = true) Integer id, Model model) {
		projet = projetDao.find(id);
		types = typeDao.getAllTypes();
		model.addAttribute("projetForm", projetForm);
		model.addAttribute(GESTIONPROJET, projet);
		model.addAttribute(LISTEPAYS, listPays);
		model.addAttribute(LISTETYPES, types);
		
		return "projet/modifierProjet";
	}
	
	/**
	 * Update project
	 * 
	 * @param projet
	 * @return Redirect to /index page to display project list
	 */
	@RequestMapping(value = "/modifierProjet", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String update(@RequestParam(name = "id", required = true) Integer id, @Valid @ModelAttribute("projetForm") ProjetForm projetForm, BindingResult bindingResultForm, @Valid @ModelAttribute("projet") Projet projet, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() || bindingResultForm.hasErrors())
		{
			types = typeDao.getAllTypes();
			model.addAttribute(GESTIONPROJET, projet);
			model.addAttribute(LISTEPAYS, listPays);
			model.addAttribute(LISTETYPES, types);
			return "projet/modifierProjet";
		}
		else
		{
			httpSession = httpServletRequest.getSession(true);
			nomEntreprise = (String)httpSession.getAttribute("nomEntreprise");
			entreprise = entrepriseDao.findEntrepriseByName(nomEntreprise);
			projet.setEntreprise(entreprise);
			Type type = typeDao.getTypeByName(projetForm.getNomType());
			projet.setType(type);
			projetDao.update(projet);
			return REDIRECTLISTEPROJET;
		}
	}
	
	
	/**
	 * Delete project
	 * 
	 * @param projet
	 * @return Redirect to /project page to display project list
	 */
	
	@RequestMapping(value = "/supprimerProjet")
	@Transactional(rollbackFor=Exception.class)
	public String delete(@RequestParam(name = "id", required = true) Integer id) {

		projetDao.delete(id);

		return REDIRECTLISTEPROJET;
	}
	
	/**
	 * Fiche d'un projet
	 * 
	 * @param id
	 * @param model
	 * @return Redirect to /ficheProjet page pour afficher la fiche d'un projet
	 */
	
	@RequestMapping(value = "/ficheProjet", method = RequestMethod.GET)
	public String ficheProjet(@RequestParam(name = "id", required = true) Integer id, Model model) {
		projet = projetDao.find(id);
		
		model.addAttribute(GESTIONPROJET, projet);
		
		return "projet/ficheProjet";
	}
	
	@RequestMapping(value = "/listeProjet", method = RequestMethod.GET)
	public String listeProjet(Model model) {
		httpSession = httpServletRequest.getSession(true);
		int id = (int)httpSession.getAttribute("id");
		List<Projet> projets = projetDao.getAllProjetsByEntreprise(id);
		
		model.addAttribute("projets", projets);
		
		return "projet/listeProjet";
	}
}

package com.portailinscription.controller;

import java.text.*;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.portailinscription.dao.impl.*;
import com.portailinscription.form.AccesForm;
import com.portailinscription.model.*;

@Controller
@RequestMapping(value = "/acces")
public class AccesController {
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	@Autowired
	private ProjetDAOImpl projetDao;
	@Autowired
	private AccesDAOImpl accesDao;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	private Acces acces;
	private AccesForm accesForm;
	private List<String> projets;
	private List<String> travailleurs;
	
	private SimpleDateFormat dt1;
	private DateFormat dateTime;
	
	private static final String ALLPROJETS = "projets";
	private static final String ALLTRAVAILLEURS = "travailleurs";
	private static final String REDIRECTLISTEACCES = "redirect:listeAcces.html";
	private static final String ALLACCES = "acces";
	
	static Logger log = Logger.getLogger(AccesController.class.getName());
	
	@PostConstruct
	public void init() {
		accesForm = new AccesForm();
		acces = new Acces();
		
		dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt1 = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
	}
	
	/**
	 *  @return /ajouterAcces page form pour ajouter un accès pour un travailleur d'une entreprise pour un projet
	 */
	@RequestMapping(value = "/ajouterAcces", method = RequestMethod.GET)
	public String add(Model model) {		
		projets = projetDao.getAllNameOfProjets();
		travailleurs = travailleurDao.getAllNameOfTravailleurs();
		
	    model.addAttribute("accesForm", new AccesForm());
	    model.addAttribute(ALLPROJETS, projets);
	    model.addAttribute(ALLTRAVAILLEURS, travailleurs);
		return "acces/ajouterAcces";
	}
	
	/**
	 * Ajouter un nouvel accès
	 * 
	 * @param accesForm
	 * @param bindingResult
	 * @param model
	 * @return Redirect to /index page pour afficher la liste d'accès
	 */
	@RequestMapping(value = "/ajouterAcces", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String add(@Valid @ModelAttribute("accesForm") AccesForm accesForm, BindingResult bindingResultForm, @Valid @ModelAttribute("acces") Acces acces, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() || bindingResultForm.hasErrors())
		{
			projets = projetDao.getAllNameOfProjets();
			travailleurs = travailleurDao.getAllNameOfTravailleurs();
			
			model.addAttribute(ALLPROJETS, projets);
			model.addAttribute(ALLTRAVAILLEURS, travailleurs);
			return "acces/ajouterAcces";
		}
		else
		{
			Acces newAcces = gestionAcces(accesForm, acces);
			accesDao.insert(newAcces);
			return REDIRECTLISTEACCES;
		}
	}
	
	/**
	 *  @return /modifierAcces page form pour modifier un accès pour un travailleur d'une entreprise pour un projet
	 */
	@RequestMapping(value = "/modifierAcces", method = RequestMethod.GET)
	public String update(@RequestParam(name = "id", required = true) Integer id, Model model) {
		acces = accesDao.find(id);
		projets = projetDao.getAllNameOfProjets();
		travailleurs = travailleurDao.getAllNameOfTravailleurs();
		
		model.addAttribute(ALLACCES, acces);
	    model.addAttribute("accesForm", accesForm);
	    model.addAttribute(ALLPROJETS, projets);
	    model.addAttribute(ALLTRAVAILLEURS, travailleurs);
		return "acces/modifierAcces";
	}
	
	/**
	 * modifier un nouvel accès
	 * 
	 * @param accesForm
	 * @param bindingResult
	 * @param model
	 * @return Redirect to /listeAcces page pour afficher la liste d'accès
	 */
	@RequestMapping(value = "/modifierAcces", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String update(@RequestParam(name = "id", required = true) Integer id, @Valid @ModelAttribute("accesForm") AccesForm accesForm, BindingResult bindingResultForm, @Valid @ModelAttribute("acces") Acces acces, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() || bindingResultForm.hasErrors())
		{
			projets = projetDao.getAllNameOfProjets();
			travailleurs = travailleurDao.getAllNameOfTravailleurs();
			
			model.addAttribute(ALLACCES, acces);
			model.addAttribute(ALLPROJETS, projets);
			model.addAttribute(ALLTRAVAILLEURS, travailleurs);
			return "acces/modifierAcces";
		}
		else
		{
			Acces modifierAcces = gestionAcces(accesForm, acces);
			accesDao.update(modifierAcces);
			return REDIRECTLISTEACCES;
		}
	}
	
	/**
	 * Suppresion de l'accès
	 * 
	 * @param id
	 * @return Redirect to /listeAcces page et supprimer l'accès d'un travailleur d'une entreprise pour un projet
	 */
	
	@RequestMapping(value = "/supprimerAcces")
	@Transactional(rollbackFor=Exception.class)
	public String delete(@RequestParam(name = "id", required = true) Integer id) {

		accesDao.delete(id);
		
		return REDIRECTLISTEACCES;
	}
	
	/**
	 * Fiche d'un accès
	 * 
	 * @param id
	 * @param model
	 * @return Redirect to /ficheAcces page pour afficher la fiche d'un projet
	 */
	
	@RequestMapping(value = "/ficheAcces", method = RequestMethod.GET)
	public String ficheProjet(@RequestParam(name = "id", required = true) Integer id, Model model) {
		acces = accesDao.find(id);
		
		model.addAttribute(ALLACCES, acces);
		
		return "acces/ficheAcces";
	}
	
	@RequestMapping(value = "/listeAcces", method = RequestMethod.GET)
	public String listeAcces(Model model) {
		HttpSession httpSession = httpServletRequest.getSession(true);
		int id = (int)httpSession.getAttribute("id");
		List<Acces> listAcces = accesDao.getAccesByEntreprise(id);
		
		model.addAttribute(ALLACCES, listAcces);
		
		return "acces/listeAcces";
	}
	
	private Acces gestionAcces(AccesForm accesForm, Acces acces)
	{
		Projet projet;
		Travailleur travailleur;
		try {
			acces.setDateDebut(dt1.parse(accesForm.getDateDebut()));
			acces.setDateFin(dt1.parse(accesForm.getDateFin()));
			acces.setDateCreation(dateTime.parse(dateTime.format(new Date())));
			acces.setDateValidation(dateTime.parse(dateTime.format(new Date())));
		} catch (ParseException e) {
			log.info(e);
			throw new IllegalArgumentException(e.getMessage());
		}
		acces.setEtatDemande("en attente de validation");
		projet = projetDao.findProjetByName(accesForm.getNomProjet());
		acces.setProjet(projet);
		travailleur = travailleurDao.getTravailleurByName(accesForm.getNomTravailleur());
		acces.setTravailleur(travailleur);
		
		return acces;
	}
}

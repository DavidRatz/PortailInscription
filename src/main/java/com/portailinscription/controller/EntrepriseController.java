package com.portailinscription.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.portailinscription.dao.impl.*;
import com.portailinscription.form.*;
import com.portailinscription.model.*;
import com.portailinscription.util.SimpleMailTest;

@Controller
@RequestMapping(value = "/entreprise")
public class EntrepriseController {

	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private DocumentDAOImpl documentDao;
	@Autowired
	private RoleDAOImpl roleDao;
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private SimpleMailTest simpleMailTest;
	
	private HttpSession httpSession;
	
	private Entreprise entreprise;
	private User user;
	private Document document;
	private Document document2;
	
	private Map<String, String> types;
	private List<String> listTypes;
	private List<String> listPays;
	
	private EntrepriseForm entrepriseForm;
	private DateFormat dateTime;
	
	private static final String GESTIONENTREPRISE = "entreprise";
	private static final String LISTEPAYS = "listPays";
	
	/**
	 * 	@param model
	 *  @return /ajouterEntreprise page form pour ajouter une demande d'inscription d'une entreprise
	 */
	@PostConstruct
	public void init() {
		types = new LinkedHashMap<>();
		listTypes = new ArrayList<>();
		listPays = new ArrayList<>();
		
		types.put("Societe sous-traitante maitre", "Société sous-traitante maître");
		types.put("Societe sous-traitante d\'une societe maitre", "Société sous-traitante d'une société maître");
		
		listPays.add("Allemagne");
		listPays.add("Belgique");
		listPays.add("Espagne");
		listPays.add("France");
		listPays.add("Luxembourg");
		listPays.add("Pays-bas");
		listPays.add("Suisse");

		listTypes.add("Société sous-traitante maître");
		listTypes.add("Société sous-traitante d'une société maître");
		
		entrepriseForm = new EntrepriseForm();
		entreprise = new Entreprise();
		
		dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@RequestMapping(value = "/ajouterEntreprise", method = RequestMethod.GET)
	public String add(Model model) {
		
		model.addAttribute("entrepriseForm", new EntrepriseForm());
		model.addAttribute(GESTIONENTREPRISE, new Entreprise());
		model.addAttribute("type", types);
		model.addAttribute(LISTEPAYS, listPays);
		
		return "entreprise/ajouterEntreprise";
	}

	/**
	 * Ajouter une nouvelle entreprise
	 * 
	 * @param entreprise
	 * @param entrepriseForm
	 * @param model
	 * @param bindingResult
	 * @return Redirect to /index page et ajouter la demande d'inscription de l'entreprise + envoyer un mail au sous-traitant
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/ajouterEntreprise", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String add(@ModelAttribute("entreprise") @Valid Entreprise entreprise, BindingResult bindingResultEntreprise, 
			@ModelAttribute("entrepriseForm") @Valid EntrepriseForm entrepriseForm, BindingResult bindingResultEntrepriseForm, Model model) throws ParseException {
		if(bindingResultEntreprise.hasErrors() || bindingResultEntrepriseForm.hasErrors()) {
			model.addAttribute("type", types); model.addAttribute(LISTEPAYS, listPays);
			return "entreprise/ajouterEntreprise";
		}
		else if(entrepriseDao.getEntrepriseByEmail(entreprise.getEmailContact()) != null) {
			model.addAttribute("type", types); model.addAttribute(LISTEPAYS, listPays); model.addAttribute("errorMail", "Cette adresse est déjà utilisée !");
			return "entreprise/ajouterEntreprise";
		}
		else {
			Role role = roleDao.findRoleByName("ROLE_SUP_1");
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user = new User();
			user.setMotPasse(passwordEncoder.encode(entrepriseForm.getMotPasse()));
			user.setRole(role);
			user.setEnabled(true);
			entreprise.setEtat("En attente");
			entreprise.setDateCreation(dateTime.parse(dateTime.format(new Date())));
			entreprise.setUtilisateur(user);
			document = new Document();
			document.setNom("Bon de commande");
			document.setNomFichier(entrepriseForm.getBonCommande());
			document.setEntreprise(entreprise);
			document2 = new Document();
			document2.setNom("Statut de la société");
			document2.setNomFichier(entrepriseForm.getStatutSociete());
			document2.setEntreprise(entreprise);
			userDao.insert(user); entrepriseDao.insert(entreprise); documentDao.insert(document); documentDao.insert(document2);
			return "redirect:../index.html";
		}}
	
	/**
	 *  @return /modifierEntreprise page form pour modifier une entreprise
	 */
	@PreAuthorize("hasRole('ROLE_SUP_1')")
	@RequestMapping(value = "/modifierEntreprise", method = RequestMethod.GET)
	public String update(@RequestParam(name = "id", required = true) Integer id, Model model) {
		entreprise = entrepriseDao.find(id);
		List<Document> documents = documentDao.getDocumentByEntreprise(entreprise.getId());
		
		EntrepriseModificationForm entrepriseModificationForm = new EntrepriseModificationForm();
		entrepriseModificationForm.setBonCommande(documents.get(0).getNomFichier());
		entrepriseModificationForm.setStatutSociete(documents.get(1).getNomFichier());
		
		model.addAttribute("entrepriseForm", entrepriseModificationForm);
		model.addAttribute(GESTIONENTREPRISE, entreprise);
		model.addAttribute("type", listTypes);
		model.addAttribute(LISTEPAYS, listPays);
		
		return "entreprise/modifierEntreprise";
	}
	
	/**
	 * Modifier entreprise
	 * 
	 * @param entrepriseModification
	 * @param entrepriseForm
	 * @param model
	 * @param bindingResult
	 * @param id
	 * @return Redirect to /ficheEntreprise page pour afficher la fiche détaillée d'une entreprise
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/modifierEntreprise", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String update(@RequestParam(name = "id", required = true) Integer id, @Valid @ModelAttribute("entrepriseForm") EntrepriseModificationForm entrepriseForm, BindingResult bindingResultEntrepriseModificationForm, @Valid @ModelAttribute("entreprise") Entreprise entrepriseModification, BindingResult bindingResult, Model model) throws ParseException {
		httpSession = httpServletRequest.getSession(true);
		entreprise = entrepriseDao.find(id);
		user = userDao.getUserByEntreprise(id);
		if(bindingResult.hasErrors() || bindingResultEntrepriseModificationForm.hasErrors())
		{
			model.addAttribute(GESTIONENTREPRISE, entrepriseModification);
			model.addAttribute("type", listTypes);
			model.addAttribute(LISTEPAYS, listPays);
			
			return "entreprise/modifierEntreprise";
		}
		else
		{
			entrepriseModification.setDateCreation(entreprise.getDateCreation());
			entrepriseModification.setEtat("En attente");
			entrepriseModification.setDateModification(dateTime.parse(dateTime.format(new Date())));
			entrepriseModification.setUtilisateur(user);
			
			document = documentDao.getDocumentByEntrepriseAndName(entrepriseModification.getId(), "Bon de commande");
			document.setNomFichier(entrepriseForm.getBonCommande());
			document.setEntreprise(entrepriseModification);
			
			document2 = documentDao.getDocumentByEntrepriseAndName(entrepriseModification.getId(), "Statut de la société");
			document2.setNomFichier(entrepriseForm.getStatutSociete());
			document2.setEntreprise(entrepriseModification);
			
			entrepriseDao.update(entrepriseModification);
			documentDao.update(document);
			documentDao.update(document2);
			
			httpSession.setAttribute("id", entrepriseModification.getId());
			httpSession.setAttribute("nomEntreprise", entrepriseModification.getNom());
			
			return "redirect:ficheEntreprise.html?id=" + id;
		}
	}
	
	
	/**
	 * Suppresion de l'entreprise
	 * 
	 * @param id
	 * @return Redirect to /index page et supprimer l'entreprise et tous les travailleurs
	 */
	@PreAuthorize("hasRole('ROLE_SUP_1')")
	@RequestMapping(value = "/supprimerEntreprise")
	@Transactional(rollbackFor=Exception.class)
	public String delete(@RequestParam(name = "id", required = true) Integer id) {

		entreprise = entrepriseDao.find(id);
		
		List<Document> documents = documentDao.getDocumentByEntreprise(id);
		for (Document doc : documents) {
			documentDao.delete(doc.getId());
		}
		user = userDao.getUserByEntreprise(id);
		entrepriseDao.delete(id);
		userDao.delete(user.getId());
		
		if("Société sous-traitante maître".equals(entreprise.getType()))
		{
			httpSession = httpServletRequest.getSession(true);
			httpSession.invalidate();
			
			return "redirect:../index.html";
		}
		else
		{			
			return "redirect:listeSousTraitant.html";
		}
	}
	
	/**
	 * Afficher la fiche détaillée d'une entreprise
	 * 
	 * @param model 
	 * @return /ficheEntreprise page
	 */
	@PreAuthorize("hasRole('ROLE_SUP_1')")
	@RequestMapping(value = "/ficheEntreprise", method = RequestMethod.GET)
	public String profile(@RequestParam(name = "id", required = true) Integer id, Model model) {
		
		entreprise = entrepriseDao.find(id);
		List<Document> documents = documentDao.getDocumentByEntreprise(entreprise.getId());
		List<Travailleur> travailleurs = travailleurDao.getAllTravailleursByEntreprise(entreprise.getId());
		
		model.addAttribute(GESTIONENTREPRISE, entreprise);
		model.addAttribute("documents", documents);
		model.addAttribute("travailleurs", travailleurs);
		
		return "entreprise/ficheEntreprise";
	}
	
	@PreAuthorize("hasRole('ROLE_SUP_1')")
	@RequestMapping(value = "/listeSousTraitant", method = RequestMethod.GET)
	public String listeSousTraitant(Model model) {
		httpSession = httpServletRequest.getSession(true);
		int id = (int)httpSession.getAttribute("id");
		if(id == 0)
		{
			return "../index.html";
		}
		List<Entreprise> entrepriseSousTraitante = entrepriseDao.getEntrepriseSousTraitanteByType("Societe sous-traitante maitre", id);
		
		model.addAttribute("entrepriseSousTraitante", entrepriseSousTraitante);
		
		return "entreprise/listeSousTraitant";
	}
}

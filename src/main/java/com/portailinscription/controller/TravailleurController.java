package com.portailinscription.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.drew.metadata.MetadataException;
import com.portailinscription.dao.impl.AccesDAOImpl;
import com.portailinscription.dao.impl.DocumentDAOImpl;
import com.portailinscription.dao.impl.EntrepriseDAOImpl;
import com.portailinscription.dao.impl.TravailleurDAOImpl;
import com.portailinscription.form.TravailleurForm;
import com.portailinscription.model.Acces;
import com.portailinscription.model.Document;
import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Travailleur;
import com.portailinscription.util.UploadFile;

@Controller
@RequestMapping(value = "/travailleur")
public class TravailleurController extends AbstractController{
	
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	@Autowired
	private DocumentDAOImpl documentDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private AccesDAOImpl accesDao;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private UploadFile uploadFile;
	
	private Entreprise entreprise;
	private Travailleur travailleur;
	private Date dateNaissance;
	
	private List<String> listEtat;
	private List<String> listFichier;
	private List<String> listNationalite;
	private List<String> listLangue;
	private TravailleurForm travailleurForm;
	
	private SimpleDateFormat dt1;
	private DateFormat dateTime;
	private HttpSession httpSession;
	
	private static final String GESTIONTRAVAILLEUR = "travailleur";
	private static final String REDIRECTLISTETRAVAILLEURS = "redirect:listeTravailleur.html";
	private static final String NOMENTREPRISECONNECTEE = "nomEntreprise";
	private static final String LISTELANGUE = "listLangue";
	private static final String LISTENATIONALITE = "listNationalite";
	
	static Logger log = Logger.getLogger(TravailleurController.class.getName());
	
	@PostConstruct
	public void init() {
		travailleur = new Travailleur();
		travailleurForm = new TravailleurForm();
		
		
		listEtat = new ArrayList<>();
		listEtat.add("En attente de validation");
		listEtat.add("Vérifié");
		listEtat.add("Validé");
		listEtat.add("Non validé");
		
		listFichier = new ArrayList<>();
		listFichier.add("E101");
		listFichier.add("Limosa");
		listFichier.add("Certificat medical");
		listFichier.add("Copie passeport");
		
		listNationalite = new ArrayList<>();
		listNationalite.add("Allemande");
		listNationalite.add("Belge");
		listNationalite.add("Espagnol");
		listNationalite.add("Française");
		listNationalite.add("Luxembourgeoise");
		listNationalite.add("Hollandaise");
		listNationalite.add("Suisse");
		
		listLangue = new ArrayList<>();
		listLangue.add("DE");
		listLangue.add("FR");
		listLangue.add("EN");
		listLangue.add("ES");
		listLangue.add("NL");
		
		dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt1 = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
	}
	
	/**
	 * 	@param model
	 *  @return /ajouterTravailleur page form pour ajouter un travailleur pour l'entreprise connectée
	 */
	@RequestMapping(value = "/ajouterTravailleur", method = RequestMethod.GET)
	public String add(@RequestParam(name = "id", required = true) Integer id, Model model) {
	

        model.addAttribute(GESTIONTRAVAILLEUR, travailleur);
        model.addAttribute("travailleurForm", new TravailleurForm());
        model.addAttribute(LISTELANGUE, listLangue);
        model.addAttribute(LISTENATIONALITE, listNationalite);
        model.addAttribute("id", id);
        
		return "travailleur/ajouterTravailleur";
	}

	/**
	 * Ajouter un nouveau travailleur
	 * 
	 * @param travailleur
	 * @param travailleurForm
	 * @param model
	 * @param bindingResult
	 * @return Redirect to /profil page pour afficher la fiche détaillée d'une entreprise et la liste des travailleurs ajoutés
	 * @throws IOException 
	 * @throws MetadataException 
	 * @throws IllegalStateException 
	 */	
	@RequestMapping(value = "/ajouterTravailleur", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String add(@RequestParam(name = "id", required = true) Integer id, @RequestParam("photoFichier") MultipartFile photoFichier, 
			@RequestParam("file") MultipartFile[] files, @Valid @ModelAttribute("travailleurForm") TravailleurForm travailleurForm, 
			BindingResult bindingResultTravailleurForm, @Valid @ModelAttribute("travailleur") Travailleur travailleur, 
			BindingResult bindingResultTravailleur, Model model, HttpServletRequest request) throws IOException, MetadataException  {
		Travailleur travailleur2;
		if(bindingResultTravailleur.hasErrors() || bindingResultTravailleurForm.hasErrors()) {
			model.addAttribute(LISTELANGUE, listLangue);
	        model.addAttribute(LISTENATIONALITE, listNationalite);
			return "travailleur/ajouterTravailleur";
		}
		else if(uploadFile.imageInvalide(request, travailleurForm.getPhotoFichier(), travailleur.getId())) {
			model.addAttribute(LISTELANGUE, listLangue);
	        model.addAttribute(LISTENATIONALITE, listNationalite);
	        model.addAttribute("travailleurForm", new TravailleurForm());
			model.addAttribute("errorImage", "La taille de l'image est trop grande");
			return "travailleur/ajouterTravailleur";
		}			
		else {
			try {
				entreprise = entrepriseDao.find(id);
				travailleur2 = new Travailleur(travailleur);
				travailleur2.setPhoto(travailleurForm.getPhotoFichier().getOriginalFilename());
				dateNaissance = dt1.parse(travailleurForm.getDateNaissance());
				travailleur2.setDateNaissance(dateNaissance);
				travailleur2.setEntreprise(entreprise);
				travailleur2.setDateCreation(dateTime.parse(dateTime.format(new Date())));
			} catch (ParseException e) {
				log.info(e.getMessage());
				throw new IllegalArgumentException(e.getMessage());
			}
			uploadFile.uploadFiles(request, travailleurForm, travailleur.getId(), listFichier, travailleur2, documentDao, travailleurDao, id);
			travailleurDao.insert(travailleur2);
			return REDIRECTLISTETRAVAILLEURS;	
		}}
	
	/**
	 * 	@param id
	 * 	@param model
	 *  @return /modifierTravailleur page form pour modifier un travailleur
	 */
	@RequestMapping(value = "/modifierTravailleur", method = RequestMethod.GET)
	public String update(@RequestParam(name = "id", required = true) Integer id, Model model) {
		travailleur = travailleurDao.find(id);
		Document document;
		travailleurForm = new TravailleurForm();
		String[] dates = new String[4];
		for (int i = 0; i < listFichier.size(); i++) {
			document = documentDao.getDocumentByTravailleurAndName(id, listFichier.get(i));
			
			travailleurForm.setDateNaissance(dt1.format(travailleur.getDateNaissance()));
			
			dates[i] = dt1.format(document.getDateValidite());
		}
		travailleurForm.setDate(dates);
        model.addAttribute("travailleurForm", travailleurForm);
        model.addAttribute(GESTIONTRAVAILLEUR, travailleur);
        model.addAttribute("listEtat", listEtat);
        model.addAttribute(LISTELANGUE, listLangue);
        model.addAttribute(LISTENATIONALITE, listNationalite);
        
		return "travailleur/modifierTravailleur";
	}
	
	/**
	 * Modifier un travailleur
	 * 
	 * @param id
	 * @param travailleurModification
	 * @param travailleurModificationForm
	 * @aram model
	 * @param bindingResult
	 * @return Redirect to /ficheTravailleur page pour afficher la fiche détaillée du travailleur modifié
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value = "/modifierTravailleur", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String update(@RequestParam(name = "id", required = true) Integer id, @RequestParam("file") MultipartFile[] files, 
			@RequestParam("photoFichier") MultipartFile photoFichier, @Valid @ModelAttribute("travailleurForm") TravailleurForm travailleurForm, 
			BindingResult bindingResultTravailleurForm, @Valid @ModelAttribute("travailleur") Travailleur travailleurModification, 
			BindingResult bindingResultTravailleur, Model model, HttpServletRequest request) throws IOException {
		Travailleur travailleurModification2;
		entreprise = entrepriseDao.findEntrepriseByTravailleur(id);
		travailleur = travailleurDao.find(id);
		if(bindingResultTravailleur.hasErrors() || bindingResultTravailleurForm.hasErrors())
		{
			model.addAttribute("listEtat", listEtat);
			model.addAttribute(GESTIONTRAVAILLEUR, travailleurModification);
			model.addAttribute(LISTELANGUE, listLangue);
	        model.addAttribute(LISTENATIONALITE, listNationalite);
	        
			return "travailleur/modifierTravailleur";
		}
		else
		{
			try {
				dateNaissance = dt1.parse(travailleurForm.getDateNaissance());
				travailleurModification2 = new Travailleur(travailleurModification);
				travailleurModification2.setDateNaissance(dateNaissance);
				travailleurModification2.setEntreprise(entreprise);
				travailleurModification2.setDateModification(dateTime.parse(dateTime.format(new Date())));
				travailleurModification2.setPhoto(travailleurForm.getPhotoFichier().getOriginalFilename());
				travailleurModification2.setDateCreation(travailleur.getDateCreation());
			} catch (ParseException e) {
				log.info(e.getMessage());
				throw new IllegalArgumentException(e.getMessage());
			}
			uploadFile.uploadFiles(request, travailleurForm, travailleurModification.getId(), listFichier, travailleurModification, documentDao, travailleurDao, id);
			travailleurDao.update(travailleurModification2);

			return REDIRECTLISTETRAVAILLEURS;
		}
	}
	
	
	/**
	 * Suppression d'un travailleur
	 * 
	 * @param id
	 * @return Redirect to /profil page pour afficher la fiche détaillée d'une entreprise et la liste des travailleurs actualisées
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	
	@RequestMapping(value = "/supprimerTravailleur")
	@Transactional(rollbackFor=Exception.class)
	public String delete(@RequestParam(name = "id", required = true) Integer id, HttpServletRequest request) throws IOException {
		travailleur = travailleurDao.find(id);
		List<Document> documents = documentDao.getDocumentByTravailleur(id);
		for (Document doc : documents) {
			documentDao.delete(doc.getId());
		}
		uploadFile.removeFiles(request, travailleur.getId());
		travailleurDao.delete(id);
		
		return REDIRECTLISTETRAVAILLEURS;
	}
	
	/**
	 * Fiche détaillée d'un travailleur
	 * 
	 * @param id
	 * @param model
	 * @return Redirect to /ficheTravailleur page pour afficher la fiche détaillée d'un travailleur
	 */
	
	@RequestMapping(value = "/ficheTravailleur", method = RequestMethod.GET)
	public String ficheTravailleur(@RequestParam(name = "id", required = true) Integer id, Model model/*, HttpSession httpSession*/) {
		travailleur = travailleurDao.find(id);
		httpSession = httpServletRequest.getSession(true);
		String nomEntreprise = (String)httpSession.getAttribute("nomEntreprise");
		List<Document> documents = documentDao.getDocumentByTravailleur(travailleur.getId());
		List<Acces> acces = accesDao.getAccesByTravailleur(travailleur.getId());
		model.addAttribute(NOMENTREPRISECONNECTEE, nomEntreprise);
		model.addAttribute("documents", documents);
		model.addAttribute(GESTIONTRAVAILLEUR, travailleur);
		model.addAttribute("acces", acces);
		
		return "travailleur/ficheTravailleur";
	}
	
	/**
	 * Afficher la liste des travailleurs
	 * 
	 * @param model 
	 * @return /listeTravailleur page
	 */
	@RequestMapping(value = "/listeTravailleur", method = RequestMethod.GET)
	public String listeTravailleur(Model model) {
		httpSession = httpServletRequest.getSession(true);
		int id = (int)httpSession.getAttribute("id");
		if(id == 0)
		{
			return "../index.html";
		}
		List<Travailleur> travailleurs = travailleurDao.getAllTravailleursByEntreprise(id);
		List<Travailleur> travailleursSousTraitant = travailleurDao.getAllTravailleursByTypeEntreprise("Societe sous-traitante d''une societe maitre", id);
		List<Entreprise> entrepriseSousTraitante = entrepriseDao.getEntrepriseSousTraitanteByType("Societe sous-traitante maitre", id);
		List<Travailleur> travailleursAvecAcces = travailleurDao.getAllTravailleursWithAcces();
		
		model.addAttribute("travailleurs", travailleurs);
		model.addAttribute("travailleursSousTraitant", travailleursSousTraitant);
		model.addAttribute("entrepriseSousTraitante", entrepriseSousTraitante);
		model.addAttribute("travailleursAvecAcces", travailleursAvecAcces);
		model.addAttribute("idEntreprise", id);
		
		return "travailleur/listeTravailleur";
	}
}

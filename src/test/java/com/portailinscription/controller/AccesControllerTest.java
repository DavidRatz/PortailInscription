package com.portailinscription.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.portailinscription.dao.impl.*;
import com.portailinscription.form.*;
import com.portailinscription.model.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:WebContent/WEB-INF/spring-security.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithMockUser(username="k.ratz@gmail.com", password="azerty", roles="SUP_1")
public class AccesControllerTest {
	
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	@Autowired
	private DocumentDAOImpl documentDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private RoleDAOImpl roleDao;
	@Autowired
	private ProjetDAOImpl projetDao;
	
	private AccesForm accesForm;
	private TravailleurForm travailleurForm;
	private ProjetForm projetForm;
	private Acces acces;
	private Projet projet;
	private Travailleur travailleur;
	private Entreprise entreprise;
	private MockMvc mockMvc;
	private List<String> listFichier;
	
	private SimpleDateFormat dt1;
	private DateFormat dateTimeFormat;
	
	@Autowired
	private WebApplicationContext wac;

	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setup() throws Exception {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
		dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt1 = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		projetForm = new ProjetForm();
		
		listFichier = new ArrayList<>();
		listFichier.add("E101");
		listFichier.add("Limosa");
		listFichier.add("Certificat medical");
		listFichier.add("Copie passeport");
	}
	
	private void ajoutPostEntreprisePage() throws Exception {
		entreprise = new Entreprise("BE123456789","Speed-Altitude", "Ratz", "Alain", "0493138881", "a.ratz@gmail.com", "Neuville-haut", "98", 6690, "Vielsalm", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
		EntrepriseForm entrepriseForm = new EntrepriseForm();
		entrepriseForm.setBonCommande("test.txt");
		entrepriseForm.setStatutSociete("lol.txt");
		entrepriseForm.setMotPasse("azerty");
		entrepriseForm.setConfirmation("azerty");
		
		ResultActions resultActions = mockMvc.perform(post("/entreprise/ajouterEntreprise")
				.with(csrf())
				.param("raisonDemande", entreprise.getRaisonDemande())
				.param("numeroBCE", entreprise.getNumeroBCE())
				.param("nom", entreprise.getNom())
				.param("nomContact", entreprise.getNomContact())
				.param("prenomContact", entreprise.getPrenomContact())
				.param("telContact", entreprise.getTelContact())
				.param("emailContact", entreprise.getEmailContact())
				.param("rue", entreprise.getRue())
				.param("numero", entreprise.getNumero())
				.param("localite", entreprise.getLocalite())
				.param("codePostal", String.valueOf(entreprise.getCodePostal()))
				.param("pays", entreprise.getPays())
				.param("type", entreprise.getType())
				.param("numeroReference", entreprise.getNumeroReference())
				.param("statutSociete", entrepriseForm.getStatutSociete())
				.param("bonCommande", entrepriseForm.getBonCommande())
				.param("motPasse", entrepriseForm.getMotPasse())
				.param("confirmation", entrepriseForm.getConfirmation())
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("../index.html"));
		resultActions.andExpect(view().name("redirect:../index.html"));
	}
	
	private void ajoutPostTravailleurPage() throws Exception {
		entreprise = entrepriseDao.findEntrepriseByName("Speed-Altitude");
		//System.setProperty("catalina.home","C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 8.5.11");
		byte[] bytes = new byte[100];
		MockMultipartFile photo = new MockMultipartFile("photoFichier", "David.png", "multipart/form-data", bytes);
		travailleur = new Travailleur(0, "Ratz", "David", "Belge", "FR", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		travailleur.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur.setEntreprise(entreprise);
		travailleurForm = new TravailleurForm();
		travailleurForm.setDateNaissance("14/04/2017");
		travailleurForm.setPhotoFichier(photo);
		
		travailleur.setPhoto(travailleurForm.getPhotoFichier().getOriginalFilename());
		String[] dates = new String[4];
		MockMultipartFile[] files = new MockMultipartFile[4];
		for (int i = 0; i < listFichier.size(); i++) {
			
			MockMultipartFile multipartFile = new MockMultipartFile("file", listFichier.get(i) + ".txt" , "multipart/form-data", bytes);
			files[i] = multipartFile;
			dates[i] = dt1.format(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		}
		
		travailleurForm.setDate(dates);
		travailleurForm.setFile(files);
		
		ResultActions resultActions = mockMvc.perform(fileUpload("/travailleur/ajouterTravailleur?id=" + entreprise.getId())
				.file(files[0]).file(files[1]).file(files[2]).file(files[3]).file(photo)
				.with(csrf())
				.param("numeroReference", String.valueOf(travailleur.getNumeroReference()))
				.param("nom", travailleur.getNom())
				.param("prenom", travailleur.getPrenom())
				.param("nationalite", travailleur.getNationalite())
				.param("langue", travailleur.getLangue())
				.param("telephone", travailleur.getTelephone())
				.param("adresseEmail", travailleur.getAdresseEmail())
				.param("dateCreation", dateTimeFormat.format(travailleur.getDateCreation()))
				.param("dateNaissance", dt1.format(travailleur.getDateNaissance()))
				.param("file", travailleurForm.getFile().toString())
				.param("photo", travailleur.getPhoto())
				.param("photoFichier", travailleurForm.getPhotoFichier().toString())
				.param("date", travailleurForm.getDate())
				.param("dateNaissance", travailleurForm.getDateNaissance())
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeTravailleur.html"));
		resultActions.andExpect(view().name("redirect:listeTravailleur.html"));
	}
	
	private void ajoutPostProjetPage() throws Exception {
		entreprise = entrepriseDao.findEntrepriseByName("Speed-Altitude");
		projet = new Projet("Maintenance serveur A", "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projetForm.setNomType("Maintenance");
		
		ResultActions resultActions = mockMvc.perform(post("/projet/ajouterProjet")
				.sessionAttr("nomEntreprise", entreprise.getNom())
				.with(csrf())
				.param("nom", projet.getNom())
				.param("rue", projet.getRue())
				.param("numero", projet.getNumero())
				.param("localite", projet.getLocalite())
				.param("codePostal", String.valueOf(projet.getCodePostal()))
				.param("pays", projet.getPays())
				.param("nomType", projetForm.getNomType())
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeProjet.html"));
		resultActions.andExpect(view().name("redirect:listeProjet.html"));
	}
	
	@Test
	public void ajoutGetAccesPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/acces/ajouterAcces")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("acces/ajouterAcces"));
		resultActions.andExpect(view().name("acces/ajouterAcces"));
		resultActions.andExpect(model().attribute("accesForm", any(AccesForm.class)));
		resultActions.andExpect(model().attribute("acces", any(Acces.class)));
		resultActions.andExpect(model().attribute("projets", any(Projet.class)));
		resultActions.andExpect(model().attribute("travailleurs", any(Travailleur.class)));
	}
	
	@Test
	public void ajoutPostAccesPage() throws Exception {
		if(entrepriseDao.findEntrepriseByName("Speed-Altitude") == null)
			ajoutPostEntreprisePage();
		if(travailleurDao.getTravailleurByName("Ratz David") == null)
			ajoutPostTravailleurPage();
		if(projetDao.findProjetByName("Maintenance serveur A") == null)
			ajoutPostProjetPage();
		acces = new Acces();
		accesForm = new AccesForm();
		accesForm.setNomProjet("Maintenance serveur A");
		accesForm.setNomTravailleur("Ratz David");
		accesForm.setDateDebut(dt1.format(dt1.parse("20/06/2017")));
		accesForm.setDateFin(dt1.format(dt1.parse("20/06/2017")));
		acces.setDateDebut(dt1.parse(accesForm.getDateDebut()));
		acces.setDateFin(dt1.parse(accesForm.getDateFin()));
		acces.setDateCreation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		acces.setDateValidation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		acces.setEtatDemande("en attente de validation");
		projet = projetDao.findProjetByName(accesForm.getNomProjet());
		acces.setProjet(projet);
		travailleur = travailleurDao.getTravailleurByName(accesForm.getNomTravailleur());
		acces.setTravailleur(travailleur);
		
		ResultActions resultActions = mockMvc.perform(post("/acces/ajouterAcces")
				.with(csrf())
				.param("dateDebut", dt1.format(acces.getDateDebut()))
				.param("dateFin", dt1.format(acces.getDateFin()))
				.param("dateCreation", dateTimeFormat.format(acces.getDateCreation()))
				.param("dateValidation", dateTimeFormat.format(acces.getDateValidation()))
				.param("etatDemande", acces.getEtatDemande())
				.param("dateDebut", accesForm.getDateDebut())
				.param("dateFin", accesForm.getDateFin())
				.param("nomProjet", accesForm.getNomProjet())
				.param("nomTravailleur", accesForm.getNomTravailleur())
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeAcces.html"));
		resultActions.andExpect(view().name("redirect:listeAcces.html"));
	}
	
	@Test
	public void ajoutPostWithErrorAccesPage() throws Exception {
		acces = new Acces();
		accesForm = new AccesForm();
		accesForm.setNomProjet("Maintenance serveur A");
		accesForm.setNomTravailleur("Ratz David");
		acces.setDateCreation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		acces.setDateValidation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		projet = projetDao.findProjetByName(accesForm.getNomProjet());
		acces.setProjet(projet);
		travailleur = travailleurDao.getTravailleurByName(accesForm.getNomTravailleur());
		acces.setTravailleur(travailleur);
		
		ResultActions resultActions = mockMvc.perform(post("/acces/ajouterAcces")
				.with(csrf())
				.param("dateCreation", dateTimeFormat.format(acces.getDateCreation()))
				.param("dateValidation", dateTimeFormat.format(acces.getDateValidation()))
				.param("etatDemande", acces.getEtatDemande())
				.param("nomProjet", accesForm.getNomProjet())
				.param("nomTravailleur", accesForm.getNomTravailleur())
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("acces/ajouterAcces"));
		resultActions.andExpect(view().name("acces/ajouterAcces"));
		resultActions.andExpect(model().attribute("projets", any(Projet.class)));
		resultActions.andExpect(model().attribute("travailleurs", any(Travailleur.class)));
	}
	
	@Test
	public void modifierGetAccesPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/acces/modifierAcces?id=1")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("acces/modifierAcces"));
		resultActions.andExpect(view().name("acces/modifierAcces"));
		resultActions.andExpect(model().attribute("accesForm", any(AccesForm.class)));
		resultActions.andExpect(model().attribute("acces", any(Acces.class)));
		resultActions.andExpect(model().attribute("projets", any(Projet.class)));
		resultActions.andExpect(model().attribute("travailleurs", any(Travailleur.class)));
	}
	
	@Test
	public void modifierPostAccesPage() throws Exception {
		acces = new Acces();
		accesForm = new AccesForm();
		accesForm.setNomProjet("Maintenance serveur A");
		accesForm.setNomTravailleur("Ratz David");
		accesForm.setDateDebut(dt1.format(dt1.parse("20/06/2017")));
		accesForm.setDateFin(dt1.format(dt1.parse("20/06/2017")));
		acces.setDateDebut(dt1.parse(accesForm.getDateDebut()));
		acces.setDateFin(dt1.parse(accesForm.getDateFin()));
		acces.setDateCreation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		acces.setDateValidation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		acces.setEtatDemande("Validé");
		projet = projetDao.findProjetByName(accesForm.getNomProjet());
		acces.setProjet(projet);
		travailleur = travailleurDao.getTravailleurByName(accesForm.getNomTravailleur());
		acces.setTravailleur(travailleur);
		
		ResultActions resultActions = mockMvc.perform(post("/acces/modifierAcces?id=1")
				.with(csrf())
				.param("dateDebut", dt1.format(acces.getDateDebut()))
				.param("dateFin", dt1.format(acces.getDateFin()))
				.param("dateCreation", dateTimeFormat.format(acces.getDateCreation()))
				.param("dateValidation", dateTimeFormat.format(acces.getDateValidation()))
				.param("etatDemande", acces.getEtatDemande())
				.param("dateDebut", accesForm.getDateDebut())
				.param("dateFin", accesForm.getDateFin())
				.param("nomProjet", accesForm.getNomProjet())
				.param("nomTravailleur", accesForm.getNomTravailleur())
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeAcces.html"));
		resultActions.andExpect(view().name("redirect:listeAcces.html"));
	}
	
	@Test
	public void modifierPostWithErrorAccesPage() throws Exception {
		acces = new Acces();
		accesForm = new AccesForm();
		accesForm.setNomProjet("Maintenance serveur A");
		accesForm.setNomTravailleur("Ratz David");
		acces.setDateCreation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		acces.setDateValidation(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
		projet = projetDao.findProjetByName(accesForm.getNomProjet());
		acces.setProjet(projet);
		travailleur = travailleurDao.getTravailleurByName(accesForm.getNomTravailleur());
		acces.setTravailleur(travailleur);
		
		ResultActions resultActions = mockMvc.perform(post("/acces/modifierAcces?id=1")
				.with(csrf())
				.param("dateCreation", dateTimeFormat.format(acces.getDateCreation()))
				.param("dateValidation", dateTimeFormat.format(acces.getDateValidation()))
				.param("etatDemande", acces.getEtatDemande())
				.param("nomProjet", accesForm.getNomProjet())
				.param("nomTravailleur", accesForm.getNomTravailleur())
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("acces/modifierAcces"));
		resultActions.andExpect(view().name("acces/modifierAcces"));
		resultActions.andExpect(model().attribute("acces", any(Acces.class)));
		resultActions.andExpect(model().attribute("projets", any(Projet.class)));
		resultActions.andExpect(model().attribute("travailleurs", any(Travailleur.class)));
	}
	
	@Test
	public void supprimerAccesPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(post("/acces/supprimerAcces?id=1")
				.with(csrf())).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeAcces.html"));
		resultActions.andExpect(view().name("redirect:listeAcces.html"));	
	}

	@Test
	public void ficheGetAccesPage() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(get("/acces/ficheAcces?id=1")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("acces/ficheAcces"));
		resultActions.andExpect(view().name("acces/ficheAcces"));
		resultActions.andExpect(model().attribute("acces", any(Acces.class)));
	}
	
	@Test
	public void listeGetAccesPage() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(get("/acces/listeAcces")
				.sessionAttr("id", 1)).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("acces/listeAcces"));
		resultActions.andExpect(view().name("acces/listeAcces"));
		resultActions.andExpect(model().attribute("acces", any(Acces.class)));	
	}
}

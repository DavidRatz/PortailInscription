package com.portailinscription.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.portailinscription.dao.impl.AccesDAOImpl;
import com.portailinscription.dao.impl.DocumentDAOImpl;
import com.portailinscription.dao.impl.EntrepriseDAOImpl;
import com.portailinscription.dao.impl.RoleDAOImpl;
import com.portailinscription.dao.impl.TravailleurDAOImpl;
import com.portailinscription.dao.impl.UserDAOImpl;
import com.portailinscription.form.EntrepriseForm;
import com.portailinscription.form.TravailleurForm;
import com.portailinscription.model.Acces;
import com.portailinscription.model.Document;
import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Travailleur;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:WebContent/WEB-INF/spring-security.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithMockUser(username="k.ratz@gmail.com", password="azerty", roles="SUP_1")
public class TravailleurControllerTest {
	
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	@Autowired
	private DocumentDAOImpl documentDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private AccesDAOImpl accesDao;
	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private RoleDAOImpl roleDao;
	
	private MockMvc mockMvc;
	
	private Entreprise entreprise;
	private List<String> listEtat;
	private List<String> listFichier;
	private List<String> listNationalite;
	private List<String> listLangue;
	
	private SimpleDateFormat dt1;
	private DateFormat dateTime;
	
	private TravailleurForm travailleurForm;
	private Travailleur travailleur;
	
	@Autowired
	private WebApplicationContext wac;
	
	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setup() throws ParseException {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
		
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
	
	private void ajoutPostEntreprisePage() throws Exception {
		entreprise = new Entreprise("BE123456789","Dcinex", "Ratz", "lol", "0493138881", "l.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
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
	
	@Test
	public void ajouterGetTravailleurPage() throws Exception {
		ajoutPostEntreprisePage();
		entreprise = entrepriseDao.find(1);
		ResultActions resultActions = mockMvc.perform(get("/travailleur/ajouterTravailleur?id=" + entreprise.getId())).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("travailleur/ajouterTravailleur"));
		resultActions.andExpect(view().name("travailleur/ajouterTravailleur"));
		resultActions.andExpect(model().attribute("travailleur", any(Travailleur.class)));
		resultActions.andExpect(model().attribute("travailleurForm", any(TravailleurForm.class)));
		resultActions.andExpect(model().attribute("listLangue", listLangue));
		resultActions.andExpect(model().attribute("listNationalite", listNationalite));
		resultActions.andExpect(model().attribute("id", entreprise.getId()));	
	}
	
	@Test
	public void ajoutPostTravailleurPage() throws Exception {
		entreprise = entrepriseDao.find(1);
		//System.setProperty("catalina.home","C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 8.5.11");
		byte[] bytes = new byte[100];
		MockMultipartFile photo = new MockMultipartFile("photoFichier", "David.png", "multipart/form-data", bytes);
		travailleur = new Travailleur(0, "Ratz", "David", "Belge", "FR", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
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
			dates[i] = dt1.format(dateTime.parse(dateTime.format(new Date())));
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
				.param("dateCreation", dateTime.format(travailleur.getDateCreation()))
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
	
	@Test
	public void ajoutPostErrorTravailleurPage() throws Exception {
		entreprise = entrepriseDao.find(1);
		//System.setProperty("catalina.home","C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 8.5.11");
		byte[] bytes = new byte[100];
		MockMultipartFile photo = new MockMultipartFile("photoFichier", "David.png", "multipart/form-data", bytes);
		travailleur = new Travailleur(0, "Ratz", "David", null, "FR", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		travailleur.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur.setEntreprise(entreprise);
		travailleurForm = new TravailleurForm();
		travailleurForm.setPhotoFichier(photo);
		
		travailleur.setPhoto(travailleurForm.getPhotoFichier().getOriginalFilename());
		String[] dates = new String[4];
		MockMultipartFile[] files = new MockMultipartFile[4];
		for (int i = 0; i < listFichier.size(); i++) {
			
			MockMultipartFile multipartFile = new MockMultipartFile("file", listFichier.get(i) + ".txt" , "multipart/form-data", bytes);
			files[i] = multipartFile;
			dates[i] = dt1.format(dateTime.parse(dateTime.format(new Date())));
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
				.param("dateCreation", dateTime.format(travailleur.getDateCreation()))
				.param("dateNaissance", dt1.format(travailleur.getDateNaissance()))
				.param("file", travailleurForm.getFile().toString())
				.param("photo", travailleur.getPhoto())
				.param("photoFichier", travailleurForm.getPhotoFichier().toString())
				.param("date", travailleurForm.getDate())
				.param("dateNaissance", travailleurForm.getDateNaissance())
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("travailleur/ajouterTravailleur"));
		resultActions.andExpect(view().name("travailleur/ajouterTravailleur"));
	}

	@Test
	public void modifierGetTravailleurPage() throws Exception {
		//ajoutPostTravailleurPage();
		ResultActions resultActions = mockMvc.perform(get("/travailleur/modifierTravailleur?id=1")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("travailleur/modifierTravailleur"));
		resultActions.andExpect(view().name("travailleur/modifierTravailleur"));
		resultActions.andExpect(model().attribute("travailleur", any(Travailleur.class)));
		resultActions.andExpect(model().attribute("travailleurForm", any(TravailleurForm.class)));
		resultActions.andExpect(model().attribute("listLangue", listLangue));
		resultActions.andExpect(model().attribute("listNationalite", listNationalite));
		resultActions.andExpect(model().attribute("listEtat", listEtat));
	}
	
	@Test
	public void modifierPostTravailleurPage() throws Exception {
		//ajoutPostTravailleurPage();
		//System.setProperty("catalina.home","C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 8.5.11");
		byte[] bytes = new byte[100];
		MockMultipartFile photo = new MockMultipartFile("photoFichier", "David.png", "multipart/form-data", bytes);
		travailleur = new Travailleur(0, "Ratz", "David", "Belge", "EN", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
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
			dates[i] = dt1.format(dateTime.parse(dateTime.format(new Date())));
		}
		
		travailleurForm.setDate(dates);
		travailleurForm.setFile(files);
		
		ResultActions resultActions = mockMvc.perform(fileUpload("/travailleur/modifierTravailleur?id=1")
				.file(files[0]).file(files[1]).file(files[2]).file(files[3]).file(photo)
				.with(csrf())
				.param("numeroReference", String.valueOf(travailleur.getNumeroReference()))
				.param("nom", travailleur.getNom())
				.param("prenom", travailleur.getPrenom())
				.param("nationalite", travailleur.getNationalite())
				.param("langue", travailleur.getLangue())
				.param("telephone", travailleur.getTelephone())
				.param("adresseEmail", travailleur.getAdresseEmail())
				.param("dateCreation", dateTime.format(travailleur.getDateCreation()))
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
	
	@Test
	public void modifierPostErrorTravailleurPage() throws Exception {
		//ajoutPostTravailleurPage();
		//System.setProperty("catalina.home","C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 8.5.11");
		byte[] bytes = new byte[100];
		MockMultipartFile photo = new MockMultipartFile("photoFichier", "David.png", "multipart/form-data", bytes);
		travailleur = new Travailleur(0, "Ratz", null, "Belge", "EN", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		travailleur.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur.setEntreprise(entreprise);
		travailleurForm = new TravailleurForm();
		travailleurForm.setPhotoFichier(photo);
		
		travailleur.setPhoto(travailleurForm.getPhotoFichier().getOriginalFilename());
		String[] dates = new String[4];
		MockMultipartFile[] files = new MockMultipartFile[4];
		for (int i = 0; i < listFichier.size(); i++) {
			
			MockMultipartFile multipartFile = new MockMultipartFile("file", listFichier.get(i) + ".txt" , "multipart/form-data", bytes);
			files[i] = multipartFile;
			dates[i] = dt1.format(dateTime.parse(dateTime.format(new Date())));
		}
		
		travailleurForm.setDate(dates);
		travailleurForm.setFile(files);
		
		ResultActions resultActions = mockMvc.perform(fileUpload("/travailleur/modifierTravailleur?id=1")
				.file(files[0]).file(files[1]).file(files[2]).file(files[3]).file(photo)
				.with(csrf())
				.param("numeroReference", String.valueOf(travailleur.getNumeroReference()))
				.param("nom", travailleur.getNom())
				.param("prenom", travailleur.getPrenom())
				.param("nationalite", travailleur.getNationalite())
				.param("langue", travailleur.getLangue())
				.param("telephone", travailleur.getTelephone())
				.param("adresseEmail", travailleur.getAdresseEmail())
				.param("dateCreation", dateTime.format(travailleur.getDateCreation()))
				.param("dateNaissance", dt1.format(travailleur.getDateNaissance()))
				.param("file", travailleurForm.getFile().toString())
				.param("photo", travailleur.getPhoto())
				.param("photoFichier", travailleurForm.getPhotoFichier().toString())
				.param("date", travailleurForm.getDate())
				.param("dateNaissance", travailleurForm.getDateNaissance())
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("travailleur/modifierTravailleur"));
		resultActions.andExpect(view().name("travailleur/modifierTravailleur"));
	}
	
	@Test
	public void supprimerTravailleurPage() throws Exception {
		ajoutPostTravailleurPage();
		Query query = em.createNativeQuery("SELECT CURRVAL('travailleur_id_seq')");
		int id = Integer.parseInt(query.getSingleResult().toString());
		em.close();
		ResultActions resultActions = mockMvc.perform(post("/travailleur/supprimerTravailleur?id=" + id)
				.with(csrf())).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeTravailleur.html"));
		resultActions.andExpect(view().name("redirect:listeTravailleur.html"));
	}
	
	@Test
	public void ficheGetTravailleurPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/travailleur/ficheTravailleur?id=1")
				.sessionAttr("nomEntreprise", "Defimedia")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("travailleur/ficheTravailleur"));
		resultActions.andExpect(view().name("travailleur/ficheTravailleur"));
		resultActions.andExpect(model().attribute("travailleur", any(Travailleur.class)));
		resultActions.andExpect(model().attribute("documents", any(Document.class)));
		resultActions.andExpect(model().attribute("acces", any(Acces.class)));
		resultActions.andExpect(model().attribute("nomEntreprise", "Defimedia"));	
	}
	
	@Test
	public void listeGetTravailleurPage() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(get("/travailleur/listeTravailleur")
				.sessionAttr("id", 1)).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("travailleur/listeTravailleur"));
		resultActions.andExpect(view().name("travailleur/listeTravailleur"));
		resultActions.andExpect(model().attribute("entrepriseSousTraitante", any(Entreprise.class)));
		resultActions.andExpect(model().attribute("travailleurs", any(Travailleur.class)));
		resultActions.andExpect(model().attribute("travailleursSousTraitant", any(Travailleur.class)));
		resultActions.andExpect(model().attribute("travailleursAvecAcces", any(Travailleur.class)));
		resultActions.andExpect(model().attribute("idEntreprise", 1));	
	}

}

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
import com.portailinscription.dao.impl.TypeDAOImpl;
import com.portailinscription.dao.impl.UserDAOImpl;
import com.portailinscription.form.EntrepriseForm;
import com.portailinscription.form.ProjetForm;
import com.portailinscription.form.TravailleurForm;
import com.portailinscription.model.Acces;
import com.portailinscription.model.Document;
import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Projet;
import com.portailinscription.model.Travailleur;
import com.portailinscription.model.Type;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:WebContent/WEB-INF/spring-security.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithMockUser(username="k.ratz@gmail.com", password="azerty", roles="SUP_1")
public class ProjetControllerTest {
	
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
	private TypeDAOImpl typeDao;
	
	private MockMvc mockMvc;
	
	private Entreprise entreprise;
	private Type type;
	private List<String> types;
	private List<String> listPays;
	
	private SimpleDateFormat dt1;
	private DateFormat dateTime;
	
	private ProjetForm projetForm;
	private Projet projet;
	
	@Autowired
	private WebApplicationContext wac;
	
	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setup() throws ParseException {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
		
		projet = new Projet();
		types = typeDao.getAllTypes();
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
	
	private void ajoutPostEntreprisePage() throws Exception {
		entreprise = new Entreprise("BE123456789","ConnectOn", "Ratz", "lol", "0493138881", "t.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
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
	public void ajouterGetProjetPage() throws Exception {
		ajoutPostEntreprisePage();
		ResultActions resultActions = mockMvc.perform(get("/projet/ajouterProjet")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("projet/ajouterProjet"));
		resultActions.andExpect(view().name("projet/ajouterProjet"));
		resultActions.andExpect(model().attribute("projet", any(Projet.class)));
		resultActions.andExpect(model().attribute("projetForm", any(ProjetForm.class)));
		resultActions.andExpect(model().attribute("types", types));
		resultActions.andExpect(model().attribute("listPays", listPays));	
	}
	
	@Test
	public void ajoutPostProjetPage() throws Exception {
		entreprise = entrepriseDao.find(2);
		projet = new Projet("Maintenance serveur A", "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projetForm.setNomType("Maintenance");
		
		ResultActions resultActions = mockMvc.perform(post("/projet/ajouterProjet")
				.sessionAttr("nomEntreprise", "Defimedia")
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
	public void ajoutPostErrorProjetPage() throws Exception {
		entreprise = entrepriseDao.find(1);
		projet = new Projet(null, "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projetForm.setNomType("Maintenance");
		
		ResultActions resultActions = mockMvc.perform(post("/projet/ajouterProjet")
				.with(csrf())
				.param("nom", projet.getNom())
				.param("rue", projet.getRue())
				.param("numero", projet.getNumero())
				.param("localite", projet.getLocalite())
				.param("codePostal", String.valueOf(projet.getCodePostal()))
				.param("pays", projet.getPays())
				.param("nomType", projetForm.getNomType())
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("projet/ajouterProjet"));
		resultActions.andExpect(view().name("projet/ajouterProjet"));
	}

	@Test
	public void modifierGetProjetPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/projet/modifierProjet?id=1")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("projet/modifierProjet"));
		resultActions.andExpect(view().name("projet/modifierProjet"));
		resultActions.andExpect(model().attribute("projet", any(Projet.class)));
		resultActions.andExpect(model().attribute("projetForm", any(ProjetForm.class)));
		resultActions.andExpect(model().attribute("types", types));
		resultActions.andExpect(model().attribute("listPays", listPays));	
	}
	
	@Test
	public void modifierPostProjetPage() throws Exception {
		entreprise = entrepriseDao.find(2);
		projet = new Projet("Maintenance serveur B", "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projetForm.setNomType("Maintenance");
		
		ResultActions resultActions = mockMvc.perform(post("/projet/modifierProjet?id=1")
				.sessionAttr("nomEntreprise", "Defimedia")
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
	public void modifierPostErrorProjetPage() throws Exception {
		entreprise = entrepriseDao.find(1);
		projet = new Projet(null, "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projetForm.setNomType("Maintenance");
		
		ResultActions resultActions = mockMvc.perform(post("/projet/modifierProjet?id=1")
				.with(csrf())
				.param("nom", projet.getNom())
				.param("rue", projet.getRue())
				.param("numero", projet.getNumero())
				.param("localite", projet.getLocalite())
				.param("codePostal", String.valueOf(projet.getCodePostal()))
				.param("pays", projet.getPays())
				.param("nomType", projetForm.getNomType())
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("projet/modifierProjet"));
		resultActions.andExpect(view().name("projet/modifierProjet"));
	}
	
	@Test
	public void supprimerProjetPage() throws Exception {
		ajoutPostProjetPage();
		Query query = em.createNativeQuery("SELECT CURRVAL('projet_id_seq')");
		int id = Integer.parseInt(query.getSingleResult().toString());
		em.close();
		ResultActions resultActions = mockMvc.perform(post("/projet/supprimerProjet?id=" + id)
				.with(csrf())).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeProjet.html"));
		resultActions.andExpect(view().name("redirect:listeProjet.html"));
	}
	
	@Test
	public void ficheGetProjetPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/projet/ficheProjet?id=1")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("projet/ficheProjet"));
		resultActions.andExpect(view().name("projet/ficheProjet"));
		resultActions.andExpect(model().attribute("projet", any(Projet.class)));
	}
	
	@Test
	public void listeGetProjetPage() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(get("/projet/listeProjet")
				.sessionAttr("id", 1)).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("projet/listeProjet"));
		resultActions.andExpect(view().name("projet/listeProjet"));
		resultActions.andExpect(model().attribute("projets", any(Projet.class)));
	}

}

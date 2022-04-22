//package com.portailinscription.controller;
//
//import static org.hamcrest.CoreMatchers.any;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.transaction.Transactional;
//
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.context.web.*;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import com.portailinscription.dao.impl.*;
//import com.portailinscription.form.ChangeMotPasseForm;
//import com.portailinscription.form.ConnectForm;
//import com.portailinscription.form.EntrepriseForm;
//import com.portailinscription.form.ProjetForm;
//import com.portailinscription.model.Entreprise;
//import com.portailinscription.model.Projet;
//import com.portailinscription.model.Role;
//import com.portailinscription.model.Travailleur;
//import com.portailinscription.model.User;
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class ConnexionControllerTest {
//	
//	@Autowired
//	private UserDAOImpl userDao;
//	@Autowired
//	private RoleDAOImpl roleDao;
//	@Autowired
//	private EntrepriseDAOImpl entrepriseDao;
//	@Autowired
//	private WebApplicationContext wac;
//	
//	private MockMvc mockMvc;
//	
//	protected MockHttpSession session;
//	
//	private DateFormat dateTime;
//	private Entreprise entreprise;
//
//	@Before
//	public void setup() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		
//		dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//	}
//	
//	public void ajoutPostEntreprisePage() throws Exception {
//		entreprise = new Entreprise("BE123456789","lol", "Ratz", "lol", "0493138881", "john.doe@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
//		EntrepriseForm entrepriseForm = new EntrepriseForm();
//		entrepriseForm.setBonCommande("test.txt");
//		entrepriseForm.setStatutSociete("lol.txt");
//		entrepriseForm.setMotPasse("pompom");
//		entrepriseForm.setConfirmation("pompom");
//		
//		ResultActions resultActions = mockMvc.perform(post("/entreprise/ajouterEntreprise")
//				.param("raisonDemande", entreprise.getRaisonDemande())
//				.param("numeroBCE", entreprise.getNumeroBCE())
//				.param("nom", entreprise.getNom())
//				.param("nomContact", entreprise.getNomContact())
//				.param("prenomContact", entreprise.getPrenomContact())
//				.param("telContact", entreprise.getTelContact())
//				.param("emailContact", entreprise.getEmailContact())
//				.param("rue", entreprise.getRue())
//				.param("numero", entreprise.getNumero())
//				.param("localite", entreprise.getLocalite())
//				.param("codePostal", String.valueOf(entreprise.getCodePostal()))
//				.param("pays", entreprise.getPays())
//				.param("type", entreprise.getType())
//				.param("numeroReference", entreprise.getNumeroReference())
//				.param("statutSociete", entrepriseForm.getStatutSociete())
//				.param("bonCommande", entrepriseForm.getBonCommande())
//				.param("motPasse", entrepriseForm.getMotPasse())
//				.param("confirmation", entrepriseForm.getConfirmation())
//				).andDo(print());
//		resultActions.andExpect(status().isFound());
//		resultActions.andExpect(redirectedUrl("../index.html"));
//		resultActions.andExpect(view().name("redirect:../index.html"));
//	}
//	
//	@Test
//	public void postConnexionPage() throws Exception {
//		ajoutPostEntreprisePage();
//		ConnectForm connectForm = new ConnectForm();
//		connectForm.setEmail("john.doe@gmail.com");
//		connectForm.setMotPasse("pompom");
//		
//		ResultActions resultActions = mockMvc.perform(post("/connexion")
//				.param("email", connectForm.getEmail())
//				.param("motPasse", connectForm.getMotPasse())
//				).andDo(print());
//		resultActions.andExpect(status().isFound());
//		resultActions.andExpect(view().name("redirect:index.html"));
//		resultActions.andExpect(redirectedUrl("index.html"));
//	}
//	
//	@Test
//	public void postWithErrorConnexionPage() throws Exception {
//		ConnectForm connectForm = new ConnectForm();
//		connectForm.setEmail("d.ratz@gmail.com");
//		
//		ResultActions resultActions = mockMvc.perform(post("/connexion")
//				.param("email", connectForm.getEmail())
//				.param("motPasse", connectForm.getMotPasse())
//				).andDo(print());
//		resultActions.andExpect(status().isOk());
//		resultActions.andExpect(view().name("connexion/connexion"));
//		resultActions.andExpect(forwardedUrl("connexion/connexion"));
//	}
//	
//	@Test
//	public void postWithNotFoundEntrepriseConnexionPage() throws Exception {
//		ConnectForm connectForm = new ConnectForm();
//		connectForm.setEmail("d.ratz@gmail.com");
//		connectForm.setMotPasse("aaaaaa");
//		
//		ResultActions resultActions = mockMvc.perform(post("/connexion")
//				.param("email", connectForm.getEmail())
//				.param("motPasse", connectForm.getMotPasse())
//				).andDo(print());
//		resultActions.andExpect(status().isOk());
//		resultActions.andExpect(view().name("connexion/connexion"));
//		resultActions.andExpect(forwardedUrl("connexion/connexion"));
//		resultActions.andExpect(model().attributeExists("error"));
//	}
//	
//	@Test
//	public void postDeconnexionPage() throws Exception {
//		
//		ResultActions resultActions = mockMvc.perform(post("/deconnexion")).andDo(print());
//		resultActions.andExpect(status().isFound());
//		resultActions.andExpect(view().name("redirect:index.html"));
//		resultActions.andExpect(redirectedUrl("index.html"));
//	}
//	
//	@Test
//	public void getModifierMotPassePage() throws Exception {
//		
//		ResultActions resultActions = mockMvc.perform(get("/modifierMotPasse")).andDo(print());
//		resultActions.andExpect(status().isOk());
//		resultActions.andExpect(view().name("connexion/modifierMotPasse"));
//		resultActions.andExpect(forwardedUrl("connexion/modifierMotPasse"));
//		resultActions.andExpect(model().attribute("changeMotPasseForm", any(ChangeMotPasseForm.class)));
//	}
//	
//	@Test
//	@Transactional
//	public void postModifierMotPassePage() throws Exception {
//		User user = new User("azeqsd");
//		Role role = roleDao.findRoleByName("Superviseur 1");
//		user.setRole(role);
//		userDao.insert(user);
//		
//		ChangeMotPasseForm changeMotPasseForm = new ChangeMotPasseForm();
//		changeMotPasseForm.setMotDePasseActuel("azeqsd");
//		changeMotPasseForm.setNouveauMotDePasse("azerty");
//		changeMotPasseForm.setConfirmationMotDePasse("azerty");
//		
//		ResultActions resultActions = mockMvc.perform(post("/modifierMotPasse")
//				.param("motDePasseActuel", changeMotPasseForm.getMotDePasseActuel())
//				.param("confirmationMotDePasse", changeMotPasseForm.getConfirmationMotDePasse())
//				.param("nouveauMotDePasse", changeMotPasseForm.getNouveauMotDePasse())
//				).andDo(print());
//		resultActions.andExpect(status().isFound());
//		resultActions.andExpect(view().name("redirect:index.html"));
//		resultActions.andExpect(redirectedUrl("index.html"));
//		
//		userDao.delete(user.getId());
//	}
//	
//	@Test
//	public void postWithErrorModifierMotPassePage() throws Exception {
//		ChangeMotPasseForm changeMotPasseForm = new ChangeMotPasseForm();
//		changeMotPasseForm.setMotDePasseActuel("azeqsd");
//		
//		ResultActions resultActions = mockMvc.perform(post("/modifierMotPasse")
//				.param("motDePasseActuel", changeMotPasseForm.getMotDePasseActuel())
//				.param("confirmationMotDePasse", changeMotPasseForm.getConfirmationMotDePasse())
//				.param("nouveauMotDePasse", changeMotPasseForm.getNouveauMotDePasse())
//				).andDo(print());
//		resultActions.andExpect(status().isOk());
//		resultActions.andExpect(view().name("connexion/modifierMotPasse"));
//		resultActions.andExpect(forwardedUrl("connexion/modifierMotPasse"));
//	}
//
//}

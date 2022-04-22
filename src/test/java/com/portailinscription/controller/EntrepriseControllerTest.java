package com.portailinscription.controller;

import static org.hamcrest.CoreMatchers.any;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
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
import com.portailinscription.service.UserDetailsServiceImpl;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:WebContent/WEB-INF/spring-security.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntrepriseControllerTest{
	
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	@Autowired
	private DocumentDAOImpl documentDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private RoleDAOImpl roleDao;
	@Autowired
	private UserDAOImpl userDao;
//	@Autowired
//	private UserDetailsServiceImpl manager;
	
	private Map<String, String> types;
	private List<String> listPays;
	private List<String> listTypes;
	private Entreprise entreprise;
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@PersistenceContext
	private EntityManager em;
	
	@Before
	public void setup() {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        
        types = new LinkedHashMap<>();
		listPays = new ArrayList<>();
		listTypes = new ArrayList<>();
		
		listTypes.add("Société sous-traitante maître");
		listTypes.add("Société sous-traitante d'une société maître");
		
		types.put("Societe sous-traitante maitre", "Société sous-traitante maître");
		types.put("Societe sous-traitante d\'une societe maitre", "Société sous-traitante d'une société maître");
		
		listPays.add("Allemagne");
		listPays.add("Belgique");
		listPays.add("Espagne");
		listPays.add("France");
		listPays.add("Luxembourg");
		listPays.add("Pays-bas");
		listPays.add("Suisse");
	}
	
	private void ajoutPostEntreprise2Page() throws Exception {
		entreprise = new Entreprise("BE123456789","Defitest", "Ratz", "lol", "0493138881", "s.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
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
	public void ajoutGetEntreprisePage() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(get("/entreprise/ajouterEntreprise")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("entreprise/ajouterEntreprise"));
		resultActions.andExpect(view().name("entreprise/ajouterEntreprise"));
		resultActions.andExpect(model().attribute("entrepriseForm", any(EntrepriseForm.class)));
		resultActions.andExpect(model().attribute("entreprise", any(Entreprise.class)));
		resultActions.andExpect(model().attribute("listPays", listPays));
		resultActions.andExpect(model().attribute("type", types));	
	}
	
	@Test
	public void ajoutPostEntreprisePage() throws Exception {
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defimedia");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defimedia");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		mockMvc.perform(post("/entreprise/supprimerEntreprise?id=" + idEntreprise2Modify)
				.with(csrf()).with(user("k.ratz@gmail.com").password("azerty").roles("SUP_1")));
		entreprise = new Entreprise("BE123456789","Defimedia", "Ratz", "lol", "0493138881", "k.ratz@gmail.com", "de mulhouse", "36", 
				4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
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
	public void ajoutPostWithErrorEntreprisePage() throws Exception {
		entreprise = new Entreprise("BE123456789", null, "Ratz", "lol", "0493138881", "k.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
		EntrepriseForm entrepriseForm = new EntrepriseForm();
		entrepriseForm.setBonCommande("test.txt");
		entrepriseForm.setStatutSociete("lol.txt");
		entrepriseForm.setMotPasse("azerty");
		entrepriseForm.setConfirmation("azerty");
		
		ResultActions resultActions = mockMvc.perform(post("/entreprise/ajouterEntreprise")
				.with(csrf())
				.param("raisonDemande", entreprise.getRaisonDemande())
				.param("nom", entreprise.getNom())
				.param("numeroBCE", entreprise.getNumeroBCE())
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
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("entreprise/ajouterEntreprise"));
		resultActions.andExpect(view().name("entreprise/ajouterEntreprise"));
		resultActions.andExpect(model().attribute("listPays", listPays));
		resultActions.andExpect(model().attribute("type", types));
	}
	
	@Test
	public void modifierGetEntreprisePage() throws Exception {
		//ajoutPostEntreprisePage();
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		
		ResultActions resultActions = mockMvc.perform(get("/entreprise/modifierEntreprise?id="+idEntreprise2Modify)).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("entreprise/modifierEntreprise"));
		resultActions.andExpect(view().name("entreprise/modifierEntreprise"));
		resultActions.andExpect(model().attribute("entrepriseForm", any(EntrepriseModificationForm.class)));
		resultActions.andExpect(model().attribute("entreprise", any(Entreprise.class)));
		resultActions.andExpect(model().attribute("listPays", listPays));
		resultActions.andExpect(model().attribute("type", listTypes));	
	}
	
	@Test
	public void modifierPostEntreprisePage() throws Exception {
		//ajoutPostEntreprisePage();
		entreprise = new Entreprise("BE123456798","Defilol", "Ratz", "David", "0493138881", "d.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
		EntrepriseModificationForm entrepriseForm = new EntrepriseModificationForm();
		entrepriseForm.setBonCommande("test.txt");
		entrepriseForm.setStatutSociete("lol.txt");
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		ResultActions resultActions = mockMvc.perform(post("/entreprise/modifierEntreprise?id="+idEntreprise2Modify)
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
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("ficheEntreprise.html?id="+idEntreprise2Modify));
		resultActions.andExpect(view().name("redirect:ficheEntreprise.html?id="+idEntreprise2Modify));	
	}
	
	@Test
	public void modifierPostWithErrorEntreprisePage() throws Exception {
		entreprise = new Entreprise("BE123456798",null, "Ratz", "David", "0493138881", "d.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
		EntrepriseModificationForm entrepriseForm = new EntrepriseModificationForm();
		entrepriseForm.setBonCommande("test.txt");
		entrepriseForm.setStatutSociete("lol.txt");
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		ResultActions resultActions = mockMvc.perform(post("/entreprise/modifierEntreprise?id="+idEntreprise2Modify)
				.with(csrf())
				.param("raisonDemande", entreprise.getRaisonDemande())
				.param("nom", entreprise.getNom())
				.param("numeroBCE", entreprise.getNumeroBCE())
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
				).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("entreprise/modifierEntreprise"));
		resultActions.andExpect(view().name("entreprise/modifierEntreprise"));
		resultActions.andExpect(model().attribute("entreprise", any(Entreprise.class)));
		resultActions.andExpect(model().attribute("listPays", listPays));
		resultActions.andExpect(model().attribute("type", listTypes));
	}
	
	//@Test
	private void modifierPostEntrepriseSousTraitantePage() throws Exception {
		entreprise = new Entreprise("BE987654321","Defilol", "Ratz", "David", "0493138881", "p.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante d'une société maître", "rkrkrk", "5e54r4z1a2", null, "BCE123456789");
		EntrepriseModificationForm entrepriseForm = new EntrepriseModificationForm();
		entrepriseForm.setBonCommande("test.txt");
		entrepriseForm.setStatutSociete("lol.txt");
		
		// Query query = em.createNativeQuery("SELECT CURRVAL('entreprise_id_seq')");
		// int id = Integer.parseInt(query.getSingleResult().toString());
		// em.close();
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		ResultActions resultActions = mockMvc.perform(post("/entreprise/modifierEntreprise?id=" + idEntreprise2Modify)
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
				.param("numeroBCEMaitre", entreprise.getNumeroBceMaitre())
				).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("ficheEntreprise.html?id=" + idEntreprise2Modify));
		resultActions.andExpect(view().name("redirect:ficheEntreprise.html?id=" + idEntreprise2Modify));	
	}
	
	@Test
	public void supprimerEntreprisePage() throws Exception {
		//ajoutPostEntreprise2Page();
		// Query query = em.createNativeQuery("SELECT CURRVAL('entreprise_id_seq')");
		// int id = Integer.parseInt(query.getSingleResult().toString());
		// em.close();
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		ResultActions resultActions = mockMvc.perform(post("/entreprise/supprimerEntreprise?id=" + idEntreprise2Modify)
				.with(csrf())
				.with(user("k.ratz@gmail.com").password("azerty").roles("SUP_1"))).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("../index.html"));
		resultActions.andExpect(view().name("redirect:../index.html"));	
	}
	
	@Test
	public void supprimerEntrepriseSousTraitantePage() throws Exception {
		//ajoutPostEntreprise2Page();
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		
		int idEntreprise2Modify = entreprise2Modify.getId();
		modifierPostEntrepriseSousTraitantePage();
		// Query query = em.createNativeQuery("SELECT CURRVAL('entreprise_id_seq')");
		// int id = Integer.parseInt(query.getSingleResult().toString());
		// em.close();
		ResultActions resultActions = mockMvc.perform(post("/entreprise/supprimerEntreprise?id=" + idEntreprise2Modify)
				.with(csrf()).with(user("k.ratz@gmail.com").password("azerty").roles("SUP_1"))).andDo(print());
		resultActions.andExpect(status().isFound());
		resultActions.andExpect(redirectedUrl("listeSousTraitant.html"));
		resultActions.andExpect(view().name("redirect:listeSousTraitant.html"));	
	}

	@Test
	public void ficheGetEntreprisePage() throws Exception {
		
		ResultActions resultActions = mockMvc.perform(get("/entreprise/ficheEntreprise?id=2")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("entreprise/ficheEntreprise"));
		resultActions.andExpect(view().name("entreprise/ficheEntreprise"));
		resultActions.andExpect(model().attribute("entreprise", any(Entreprise.class)));
		resultActions.andExpect(model().attribute("documents", any(Document.class)));
		resultActions.andExpect(model().attribute("travailleurs", any(Travailleur.class)));	
	}
	
	@Test
	public void listeGetEntreprisePage() throws Exception {
		//ajoutPostEntreprise2Page();
		Entreprise entreprise2Modify = null;
		try{
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}catch(NoResultException ex){
			ajoutPostEntreprise2Page();
			entreprise2Modify = entrepriseDao.findEntrepriseByName("Defitest");
		}
		int idEntreprise2Modify = entreprise2Modify.getId();
		modifierPostEntrepriseSousTraitantePage();
		// Query query = em.createNativeQuery("SELECT CURRVAL('entreprise_id_seq')");
		// int id = Integer.parseInt(query.getSingleResult().toString());
		// em.close();
		ResultActions resultActions = mockMvc.perform(get("/entreprise/listeSousTraitant")
				.sessionAttr("id", idEntreprise2Modify)).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(forwardedUrl("entreprise/listeSousTraitant"));
		resultActions.andExpect(view().name("entreprise/listeSousTraitant"));
		resultActions.andExpect(model().attribute("entrepriseSousTraitante", any(Entreprise.class)));	
	}
}

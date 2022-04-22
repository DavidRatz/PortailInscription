package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Projet;
import com.portailinscription.model.Role;
import com.portailinscription.model.Type;
import com.portailinscription.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class ProjetDAOImplTest {

	@Autowired
	private ProjetDAOImpl projetDao;
	@Autowired
	private RoleDAOImpl roleDao;
	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private TypeDAOImpl typeDao;
	
	private MockMvc mockMvc;
	
	private Entreprise entreprise;
	private Projet projet;
	private User user;
	private DateFormat dateTime;
	
	@Autowired
	private WebApplicationContext wac;
	
	
	@Before
	public void setup() throws ParseException {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Role role = roleDao.findRoleByName("ROLE_SUP_1");
		Type type = typeDao.getTypeByName("Maintenance");
		
		
		user = new User();
		user.setMotPasse("abcdef");
		user.setRole(role);
		userDao.insert(user);
		
		entreprise = new Entreprise("BE123456789","ConnectOn", "Ratz", "lol", "0493138881", "k.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
		entreprise.setEtat("En attente");
		entreprise.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		entreprise.setUtilisateur(user);
		entrepriseDao.insert(entreprise);
		
		projet = new Projet("Maintenance du serveur A", "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projet.setType(type);
		projetDao.insert(projet);
		
	}
	
	@After
	public void tearDown(){
		userDao.delete(user.getId());
		entrepriseDao.delete(entreprise.getId());
		projetDao.delete(projet.getId());
	}
	
	@Test
	public void getAllProjetsTest() throws Exception {
		
		List<Projet> listProjets = projetDao.getAllProjets();
		assertNotNull(listProjets);
		assertTrue("La liste ne contient pas un projet !!!", listProjets.size() >= 1);
	}
	
	@Test
	public void getProjetByNameTest() throws Exception {
		
		Projet projetFound = projetDao.findProjetByName("Maintenance du serveur A");
		assertNotNull(projetFound);
		assertSame("Les ids ne sont pas identiques!!!", projetFound.getId(), projet.getId());
	}
	
	@Test(expected = NoResultException.class)
	public void getProjetByNameExceptionTest() throws Exception {
		
		Projet projetFound = projetDao.findProjetByName("Maintenance du serveur B");
		assertNotNull(projetFound);
		assertSame("Les ids ne sont pas identiques!!!", projetFound.getId(), projet.getId());
	}
	
	@Test
	public void getProjetByEntrepriseTest() throws Exception {
		
		List<Projet> listProjetsByEntreprise = projetDao.getAllProjetsByEntreprise(entreprise.getId());
		assertNotNull(listProjetsByEntreprise);
		assertTrue("La liste ne contient pas un projet pour une entreprise !!!", listProjetsByEntreprise.size() >= 1);
	}
}

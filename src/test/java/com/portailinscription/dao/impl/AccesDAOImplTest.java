package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import com.portailinscription.model.Acces;
import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Projet;
import com.portailinscription.model.Role;
import com.portailinscription.model.Travailleur;
import com.portailinscription.model.Type;
import com.portailinscription.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class AccesDAOImplTest {

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
	@Autowired
	private AccesDAOImpl accesDao;
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	
	private MockMvc mockMvc;
	
	private Entreprise entreprise;
	private Projet projet;
	private User user;
	private Travailleur travailleur;
	private Acces acces;
	private DateFormat dateTime;
	private SimpleDateFormat dt1;
	
	@Autowired
	private WebApplicationContext wac;
	
	
	@Before
	public void setup() throws ParseException {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dt1 = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
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
		
		travailleur = new Travailleur(0, "Ratz", "David", "Belge", "FR", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		travailleur.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur.setPhoto("David.png");
		travailleur.setEntreprise(entreprise);
		travailleurDao.insert(travailleur);
		
		acces = new Acces();
		acces.setDateDebut(dt1.parse("24/04/2017"));
		acces.setDateFin(dt1.parse("24/04/2017"));
		acces.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		acces.setDateValidation(dateTime.parse(dateTime.format(new Date())));
		acces.setEtatDemande("en attente de validation");
		acces.setProjet(projet);
		acces.setTravailleur(travailleur);
		accesDao.insert(acces);
	}
	
	@After
	public void tearDown(){
		userDao.delete(user.getId());
		entrepriseDao.delete(entreprise.getId());
		projetDao.delete(projet.getId());
		travailleurDao.delete(travailleur.getId());
		accesDao.delete(acces.getId());
	}
	
	@Test
	public void getAllAccesTest() throws Exception {
		
		List<Acces> listAcces = accesDao.getAllAcces();
		assertNotNull(listAcces);
		assertTrue("La liste ne contient pas d'accès !!!", listAcces.size() >= 1);
	}
	
	@Test
	public void getAccesByTravailleurTest() throws Exception {
		
		List<Acces> listAccesByTravailleur = accesDao.getAccesByTravailleur(travailleur.getId());
		assertNotNull(listAccesByTravailleur);
		assertTrue("La liste ne contient pas d'accès pour ce travailleur !!!", listAccesByTravailleur.size() >= 1);
	}
	
	@Test
	public void getAccesByEntrepriseTest() throws Exception {
		
		List<Acces> listAccesByEntreprise = accesDao.getAccesByEntreprise(entreprise.getId());
		assertNotNull(listAccesByEntreprise);
		assertTrue("La liste ne contient pas d'accès pour cette entreprise !!!", listAccesByEntreprise.size() >= 1);
	}
}

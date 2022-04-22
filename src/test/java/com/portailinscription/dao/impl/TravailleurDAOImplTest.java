package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class TravailleurDAOImplTest {

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
	private Entreprise entreprise2;
	private Projet projet;
	private User user;
	private Travailleur travailleur;
	private Travailleur travailleur2;
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
		
		entreprise = new Entreprise("BE123456789","ConnectOn", "Ratz", "lol", "0493138881", "k.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Societe sous-traitante maitre", "rkrkrk", "5e54r4z1a2", null, null);
		entreprise.setEtat("En attente");
		entreprise.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		entreprise.setUtilisateur(user);
		entrepriseDao.insert(entreprise);
		
		entreprise2 = new Entreprise("BE987654321","Defi", "Ratz", "David", "0493138881", "d.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Societe sous-traitante d\'une societe maitre", "rkrkrk", "5e54r4z1a2", null, "BE123456789");
		entreprise2.setEtat("En attente");
		entreprise2.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		entreprise2.setUtilisateur(user);
		entrepriseDao.insert(entreprise2);
		
		projet = new Projet("Maintenance du serveur A", "du parc", "28", 4100, "Liège", "Belgique");
		projet.setEntreprise(entreprise);
		projet.setType(type);
		projetDao.insert(projet);
		
		travailleur = new Travailleur(0, "Ratz", "Bryan", "Belge", "FR", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		travailleur.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur.setPhoto("David.png");
		travailleur.setEntreprise(entreprise);
		travailleurDao.insert(travailleur);
		
		travailleur2 = new Travailleur(0, "Doe", "John", "Belge", "FR", "0478484547", "j.doe@gmail.com");
		travailleur2.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		travailleur2.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur2.setPhoto("David.png");
		travailleur2.setEntreprise(entreprise2);
		travailleurDao.insert(travailleur2);
		
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
		entrepriseDao.delete(entreprise2.getId());
		projetDao.delete(projet.getId());
		travailleurDao.delete(travailleur.getId());
		travailleurDao.delete(travailleur2.getId());
		accesDao.delete(acces.getId());
	}
	
	@Test
	public void getAllTravailleursTest() throws Exception {
		
		List<Travailleur> listTravailleurs = travailleurDao.getAllTravailleurs();
		assertNotNull(listTravailleurs);
		assertTrue("La liste ne contient pas de travailleur !!!", listTravailleurs.size() >= 1);
	}
	
	@Test
	public void getTravailleurByNameTest() throws Exception {
		
		Travailleur travailleurFound = travailleurDao.getTravailleurByName("Ratz Bryan");
		assertNotNull(travailleurFound);
		assertSame("Les ids sont identiques!!!", travailleurFound.getId(), travailleur.getId());
	}
	
	@Test(expected = NoResultException.class)
	public void getTravailleurByNameExceptionTest() throws Exception {
		
		Travailleur travailleurFound = travailleurDao.getTravailleurByName("Ratz Jean");
		assertNotNull(travailleurFound);
		assertSame("Les ids sont identiques!!!", travailleurFound.getId(), travailleur.getId());
	}
	
	@Test
	public void getTravailleurByEntrepriseTest() throws Exception {
		
		List<Travailleur> listTravailleurByEntreprise = travailleurDao.getAllTravailleursByEntreprise(entreprise.getId());
		assertNotNull(listTravailleurByEntreprise);
		assertTrue("La liste ne contient pas de travailleur pour cette entreprise !!!", listTravailleurByEntreprise.size() >= 1);
	}
	
	@Test
	public void getTravailleurByTypeEntrepriseTest() throws Exception {
		List<Entreprise> listEntreprise = entrepriseDao.getAllEntreprises();
		List<Travailleur> listTravailleur = travailleurDao.getAllTravailleursByEntreprise(30);
		List<Travailleur> listTravailleurByTypeEntreprise = travailleurDao.getAllTravailleursByTypeEntreprise("Societe sous-traitante d\'une societe maitre", entreprise.getId());
		assertNotNull(listTravailleurByTypeEntreprise);
		assertTrue("La liste ne contient pas de travailleur pour une entreprise sous-traitante !!!", listTravailleurByTypeEntreprise.size() >= 1);
	}
	
	@Test
	public void getTravailleurWithAccesTest() throws Exception {
		
		List<Travailleur> listTravailleurWithAcces = travailleurDao.getAllTravailleursWithAcces();
		assertNotNull(listTravailleurWithAcces);
		assertTrue("La liste ne contient pas de travailleur pour cette entreprise !!!", listTravailleurWithAcces.size() >= 1);
	}
}

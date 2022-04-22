package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import com.portailinscription.model.Entreprise;
import com.portailinscription.model.Role;
import com.portailinscription.model.Travailleur;
import com.portailinscription.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class EntrepriseDAOImplTest {

	@Autowired
	private RoleDAOImpl roleDao;
	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private TravailleurDAOImpl travailleurDao;
	
	private MockMvc mockMvc;
	
	private Entreprise entreprise;
	private Entreprise entreprise2;
	private User user;
	private Travailleur travailleur;
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
		
		user = new User();
		user.setMotPasse("abcdef");
		user.setRole(role);
		userDao.insert(user);
		
		entreprise = new Entreprise("BE123456789","Defimedia2", "Ratz", "Kevin", "0493138881", "k.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Société sous-traitante maître", "rkrkrk", "5e54r4z1a2", null, null);
		entreprise.setEtat("En attente");
		entreprise.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		entreprise.setUtilisateur(user);
		entrepriseDao.insert(entreprise);
		
		entreprise2 = new Entreprise("BE987654321","Defi", "Ratz", "David", "0493138881", "d.ratz@gmail.com", "de mulhouse", "36", 4000, "Liege", "Belgique", null, "Societe sous-traitante d\'une societe maitre", "rkrkrk", "5e54r4z1a2", null, "BE123456789");
		entreprise2.setEtat("En attente");
		entreprise2.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		entreprise2.setUtilisateur(user);
		entrepriseDao.insert(entreprise2);
		
		travailleur = new Travailleur(0, "Ratz", "David", "Belge", "FR", "0478484547", "d.ratz@gmail.com");
		travailleur.setDateCreation(dateTime.parse(dateTime.format(new Date())));
		travailleur.setDateNaissance(dt1.parse("14/04/2017"));
		travailleur.setPhoto("David.png");
		travailleur.setEntreprise(entreprise);
		travailleurDao.insert(travailleur);
	}
	
	@After
	public void tearDown(){
		userDao.delete(user.getId());
		entrepriseDao.delete(entreprise.getId());
		entrepriseDao.delete(entreprise2.getId());
		travailleurDao.delete(travailleur.getId());
	}
	
	@Test
	public void getAllEntrepriseTest() throws Exception {
		
		List<Entreprise> listEntreprise = entrepriseDao.getAllEntreprises();
		assertNotNull(listEntreprise);
		assertTrue("La liste ne contient pas d'entreprise !!!", listEntreprise.size() >= 1);
	}
	
	@Test
	public void getEntrepriseByNameTest() throws Exception {
		
		Entreprise entrepriseFound = entrepriseDao.findEntrepriseByName("Defimedia2");
		assertNotNull(entrepriseFound);
		assertSame("Les ids sont identiques!!!", entrepriseFound.getId(), entreprise.getId());
	}
	
	@Test(expected = NoResultException.class)
	public void getEntrepriseByNameExceptionTest() throws Exception {
		
		Entreprise entrepriseFound = entrepriseDao.findEntrepriseByName("test");
		assertNotNull(entrepriseFound);
		assertSame("Les ids sont identiques!!!", entrepriseFound.getId(), entreprise.getId());
	}
	
	@Test
	public void getEntrepriseByEmailAndPasswordTest() throws Exception {
		
		Entreprise entrepriseFound = entrepriseDao.getEntrepriseByEmailAndPassword("k.ratz@gmail.com", "abcdef");
		assertNotNull(entrepriseFound);
		assertSame("Les ids sont identiques!!!", entrepriseFound.getId(), entreprise.getId());
	}
	
	@Test
	public void getEntrepriseByEmailAndPasswordExceptionTest() throws Exception {
		
		Entreprise entrepriseFound = entrepriseDao.getEntrepriseByEmailAndPassword("j.ratz@gmail.com", "azerty");
		assertNull(entrepriseFound);
	}
	
	@Test(expected = NoResultException.class)
	public void getEntrepriseByTravailleurExceptionTest() throws Exception {
		
		Entreprise entrepriseFound = entrepriseDao.findEntrepriseByTravailleur(0);
		assertNotNull(entrepriseFound);
		assertSame("Les ids sont identiques!!!", entrepriseFound.getId(), entreprise.getId());
	}
	
	@Test
	public void getEntrepriseByTravailleurTest() throws Exception {
		
		Entreprise entrepriseFound = entrepriseDao.findEntrepriseByTravailleur(travailleur.getId());
		assertNotNull(entrepriseFound);
		assertSame("Les ids sont identiques!!!", entrepriseFound.getId(), entreprise.getId());
	}
}

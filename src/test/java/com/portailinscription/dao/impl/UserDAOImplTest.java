package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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

import com.portailinscription.model.Role;
import com.portailinscription.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class UserDAOImplTest {

	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private RoleDAOImpl roleDao;
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	private User user;
	private User user2;
	
	@Before
	public void setup() {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		user = new User("azerty");
		Role role = roleDao.findRoleByName("ROLE_SUP_1");
		user.setRole(role);
		user.setEnabled(true);
		userDao.insert(user);
		
		user2 = new User("azeqsd");
		user2.setRole(role);
		userDao.insert(user2);
	}
	
	@After
	public void tearDown(){
		userDao.delete(user.getId());
		userDao.delete(user2.getId());
	}
	
	@Test
	public void getAllUsersTest() throws Exception {
		
		List<User> listUsers = userDao.getAllUsers();
		assertNotNull(listUsers);
		assertTrue("La liste contient bien un utilisateur !!!", listUsers.size() >= 1);
		
		
	}
	
	@Test
	public void getUserByPasswordTest() throws Exception {
		
		User userFound = userDao.findUserByPassword("azeqsd");
		assertNotNull(userFound);
		assertSame("Les ids sont identiques!!!", userFound.getId(), user2.getId());
		
	}
	
	@Test(expected = NoResultException.class)
	public void getUserByPasswordExceptionTest() throws Exception {
		
		User userFound = userDao.findUserByPassword("");
		assertNotNull(userFound);
		assertSame("Les ids sont identiques!!!", userFound.getId(), user.getId());
		
	}
	
	@Test(expected = NoResultException.class)
	public void getUserByEntrepriseExceptionTest() throws Exception {
		
		User userFound = userDao.getUserByEntreprise(0);
		assertNotNull(userFound);
		assertSame("Les ids sont identiques!!!", userFound.getId(), user.getId());
		
	}
}

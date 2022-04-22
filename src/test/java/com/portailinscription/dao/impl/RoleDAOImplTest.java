package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RoleDAOImplTest {
	
	@Autowired
	private RoleDAOImpl roleDao;
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void getAllRoleTest() throws Exception {	
		List<Role> listRoles = roleDao.getAllRoles();
		
		assertSame(1, listRoles.size());
		assertNotNull(listRoles);
	}
	
	@Test
	public void getRoleByNameTest() throws Exception {
		Role role = roleDao.findRoleByName("ROLE_SUP_1");
		
		assertTrue("Le role recherché est identique","ROLE_SUP_1".equals(role.getNom()));
		assertSame(1, role.getId());
		assertNotNull(role);
	}
}

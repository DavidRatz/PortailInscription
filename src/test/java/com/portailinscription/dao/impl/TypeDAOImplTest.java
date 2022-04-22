package com.portailinscription.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
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

import com.portailinscription.model.Type;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TypeDAOImplTest {
	
	@Autowired
	private TypeDAOImpl typeDao;
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void getAllTypeTest() throws Exception {	
		List<String> listTypes = typeDao.getAllTypes();
		
		assertSame(2, listTypes.size());
		assertNotNull(listTypes);
	}
	
	@Test
	public void getTypeByNameTest() throws Exception {
		Type type = typeDao.getTypeByName("Maintenance");
		
		assertTrue("Le type recherché n'est pas identique","Maintenance".equals(type.getNom()));
		assertSame(1, type.getId());
		assertNotNull(type);
	}
	
	@Test(expected = NoResultException.class)
	public void getTypeByNameExceptionTest() throws Exception {
		Type type = typeDao.getTypeByName("lol");
		
		assertTrue("Le type recherché n'est pas identique","Maintenance".equals(type.getNom()));
		assertSame(1, type.getId());
		assertNotNull(type);
	}
}

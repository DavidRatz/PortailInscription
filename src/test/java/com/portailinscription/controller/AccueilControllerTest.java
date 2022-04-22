package com.portailinscription.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.portailinscription.dao.impl.*;
import com.portailinscription.form.ProjetForm;
import com.portailinscription.model.Projet;
import com.portailinscription.model.Travailleur;

public class AccueilControllerTest {
	
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
 
        mockMvc = MockMvcBuilders.standaloneSetup(new AccueilController())
                                 .setViewResolvers(viewResolver)
                                 .build();
	}
	
	@Test
	public void testIndexPage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/index")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(view().name("index"));
		resultActions.andExpect(forwardedUrl("index.ftl"));
		
	}
	
	@Test
	public void testHomePage() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/")).andDo(print());
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(view().name("index"));
		resultActions.andExpect(forwardedUrl("index.ftl"));
		
	}
/*
	@Test
	public void testAjouterProjetPage() throws Exception {
		Projet projet = new Projet();
		ProjetForm projetForm = new ProjetForm();
		List<String> types = new ArrayList<>();
		
		types.add("En attente");
		types.add("Vérifié");
		types.add("Validé");
		types.add("Refusée");
		
		List<String> listPays = new ArrayList<>();
		
		listPays.add("Allemagne");
		listPays.add("Belgique");
		listPays.add("Espagne");
		listPays.add("France");
		listPays.add("Luxembourg");
		listPays.add("Pays-bas");
		listPays.add("Suisse");
		ResultActions resultActions = mockMvc.perform(get("projet/ajouterProjet")).andDo(print());
		//resultActions.andExpect(status().isOk());
		resultActions.andExpect(view().name("projet/ajouterProjet"));
		resultActions.andExpect(model().attribute("projet", projet));
		resultActions.andExpect(model().attribute("projetForm", projetForm));
		resultActions.andExpect(model().attribute("types", types));
		resultActions.andExpect(model().attribute("listPays", listPays));
	}*/

}

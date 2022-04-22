package com.portailinscription.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.portailinscription.dao.impl.EntrepriseDAOImpl;
import com.portailinscription.dao.impl.UserDAOImpl;
import com.portailinscription.form.ChangeMotPasseForm;
import com.portailinscription.form.ConnectForm;
import com.portailinscription.model.Entreprise;
import com.portailinscription.model.User;
import com.portailinscription.service.UserDetailsServiceImpl;

@Controller
public class ConnexionController extends AbstractController{
	
	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private EntrepriseDAOImpl entrepriseDao;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private UserDetailsServiceImpl manager;
	
	private HttpSession httpSession;
	
	private static final String REDIRECTINDEX = "redirect:index.html";
	private static final String CONNEXION = "connexion/connexion";
	/**
	 * 	@param model
	 *  @return /connection page form to connect user
	 */
	@RequestMapping(value = "/connexion", method = RequestMethod.GET)
	public String connexion(Model model, String error) {
		if(estConnecte())
		{
			return REDIRECTINDEX;
		}
		else
		{
			if (error != null)
	            model.addAttribute("error", error);
			ConnectForm connectForm = new ConnectForm();
			model.addAttribute("connectForm", connectForm);
			return CONNEXION;
		}
	}
	
	/**
	 * connexion utilisateur
	 * 
	 * @param connectForm
	 * @param model
	 * @param bindingResult
	 * @return Redirect to /index page avec connexion de l'utilisateur
	 */
	@RequestMapping(value = "/connexion", method = RequestMethod.POST)
	public String connexion(@Valid @ModelAttribute("connectForm") ConnectForm connectForm, BindingResult bindingResult, Model model) {
		httpSession = httpServletRequest.getSession(true);
		httpSession.invalidate();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(bindingResult.hasErrors())
		{
			return "redirect:connexion";
		}
		else
		{
			UserDetails userDetails;
			try{
				userDetails = manager.loadUserByUsername(connectForm.getUsername());
				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				if(passwordEncoder.matches(connectForm.getPassword(), userDetails.getPassword()))
				{
					Entreprise entrepriseFound = entrepriseDao.getEntrepriseByEmailAndPassword(userDetails.getUsername(),userDetails.getPassword());
					httpSession = httpServletRequest.getSession(true);
					httpSession.setAttribute("id", entrepriseFound.getId());
					httpSession.setAttribute("nomEntreprise", entrepriseFound.getNom());
					return REDIRECTINDEX;
				}
				
				return "redirect:connexion";
				
			}
			catch(UsernameNotFoundException ex)
			{
				model.addAttribute("error", ex.getMessage());
				return "redirect:connexion";
			}
		}
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String error403(Model model) {
		
		return "403";
	}
	
	/**
	 * deconnection utilisateur
	 * 
	 * @return Redirect to /index page pour afficher la page d'accueil
	 */
	
	@RequestMapping(value = "/deconnexion")
	public String deconnexion(HttpServletRequest request, HttpServletResponse response) {

		httpSession = httpServletRequest.getSession(true);
		httpSession.invalidate();
		Cookie sessionCookie = new Cookie("JSESSIONID", null);
		sessionCookie.setPath("/");
		sessionCookie.setMaxAge(0);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
			new CookieClearingLogoutHandler(sessionCookie).logout(request, response, auth);
		}
		
		return REDIRECTINDEX;
	}
	
	/**
	 *	@param model
	 *  @return /modifierMotPasse page form pour modifier le mot de passe
	 */
	
	@RequestMapping(value = "/modifierMotPasse", method = RequestMethod.GET)
	public String modifierMotPasse(Model model) {

		ChangeMotPasseForm changeMotPasseForm = new ChangeMotPasseForm();
		
		model.addAttribute("changeMotPasseForm", changeMotPasseForm);
		
		return "connexion/modifierMotPasse";
	}
	
	/**
	 * Modification du mot de passe de l'utilisateur
	 * 
	 * @param model
	 * @param changeMotPasseForm
	 * @param bindingResult
	 * @return Redirect to /index page pour afficher la page d'accueil
	 */
	
	@RequestMapping(value = "/modifierMotPasse", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)
	public String modifierMotPasse(@Valid @ModelAttribute("changeMotPasseForm") ChangeMotPasseForm changeMotPasseForm, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors())
		{
			return "connexion/modifierMotPasse";
		}
		else
		{
			User user = userDao.findUserByPassword(changeMotPasseForm.getMotDePasseActuel());
			user.setMotPasse(changeMotPasseForm.getNouveauMotDePasse());
			
			userDao.update(user);
			
			return REDIRECTINDEX;
		}
	}
}

package com.portailinscription.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portailinscription.dao.impl.RoleDAOImpl;
import com.portailinscription.dao.impl.UserDAOImpl;
import com.portailinscription.model.Role;
import com.portailinscription.model.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAOImpl userDao;
	@Autowired
	private RoleDAOImpl roleDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userDao.findUserByUsername(username);
		
		if(user!=null){
			String password = user.getMotPasse();
			boolean enabled = user.isEnabled();
			boolean accountNonExpired = user.isEnabled();
			boolean credentialsNonExpired = user.isEnabled();
			boolean accountNonLocked = user.isEnabled();
			
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for(Role role : roleDao.findRolesByIdUtilisateur(user.getId())){
				authorities.add(new SimpleGrantedAuthority(role.getNom()));
			}
			
			org.springframework.security.core.userdetails.User securityUser = new 
					org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
			return securityUser;
		}else{
			throw new UsernameNotFoundException("Le nom d'utilisateur ou le mot de passe saisi est incorrect !!!");
		}
	}
}

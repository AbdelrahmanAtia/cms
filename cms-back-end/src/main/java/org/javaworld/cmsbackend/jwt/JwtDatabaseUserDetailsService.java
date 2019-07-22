package org.javaworld.cmsbackend.jwt;

import org.javaworld.cmsbackend.dao.UserRepository;
import org.javaworld.cmsbackend.entity.User;
import org.javaworld.cmsbackend.jwt.resource.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtDatabaseUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User theUser = userRepository.findByUsername(username);
		
		if(theUser == null) {
			throw new AuthenticationException("Invalid username or password", null);
		}
			
		return theUser;
	}

}

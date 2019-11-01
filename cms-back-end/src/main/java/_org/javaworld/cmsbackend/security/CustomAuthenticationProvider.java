package _org.javaworld.cmsbackend.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Lazy
	@Autowired
	HttpServletRequest httpServletRequest;
	
	
	@Lazy
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("starting authenticate():CustomAuthenticationProvider...");
		
		String jwtToken = httpServletRequest.getHeader("jwt-token");
		if(jwtToken == null) {
			return null;
		} 
		
		try {
			jwtTokenUtil.validateToken(jwtToken);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(null, null);
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("starting supports()...");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

package _org.javaworld.cmsbackend.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class WsBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Lazy
	@Autowired
	HttpServletResponse httpServletResponse;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		System.out.println("starting commence()..");
		httpServletResponse.setStatus(401); //unautherized
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("dev");
		super.afterPropertiesSet();
	}

}

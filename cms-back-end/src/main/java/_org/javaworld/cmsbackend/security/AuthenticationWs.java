package _org.javaworld.cmsbackend.security;

import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationWs {

	@Lazy
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Lazy
	@Autowired
	HttpServletResponse httpServletResponse;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Response login(@RequestBody LoginInfo loginInfo) {

		System.out.println("starting login()..");
		System.out.println("login info: " + loginInfo);
		String userName = loginInfo.getUserName();
		String userPassword = loginInfo.getUserPassword();
		
		if(userName.equals("user") && userPassword.equals("123")) {
			//generate jwt token..
	        String jwtToken = jwtTokenUtil.generateToken(userName);
	        System.out.println("generated jwt token: " + jwtToken);
	        httpServletResponse.setHeader("jwt-token", jwtToken );
	        return new Response(true, "user authenticated successfully..");
	        
		} else {
	        return new Response(false, "authentication failed..");
		}
		
		//dont forget to add usernamepassword token to security context holder..
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	}

}

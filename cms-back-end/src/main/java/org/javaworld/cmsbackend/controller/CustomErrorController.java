package org.javaworld.cmsbackend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@RestController
@RequestMapping("")
public class CustomErrorController implements ErrorController {

	@Lazy
	@Autowired
	HttpServletResponse res;

	@RequestMapping("/error")
	public Response handleError(HttpServletRequest request) {
		int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		System.out.println("statusCode = " + statusCode);

		String message = (String) request.getAttribute("javax.servlet.error.message");
		System.out.println("message = " + message);

		Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (t != null) {
			System.out.println("exception class = " + t.getClass());
			System.out.println("exception message = " + t.getMessage());

			Class cl = t.getClass();
			if (cl.equals(BadCredentialsException.class) || cl.equals(ExpiredJwtException.class)
					|| cl.equals(SignatureException.class)) {
				res.setStatus(401);
			} else {
				res.setStatus(statusCode);
			}
		}
		return new Response(statusCode + "", message);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}

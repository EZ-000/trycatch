package com.ssafy.trycatch;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/githublogin")
	public ResponseEntity<String> githubLogin(@PathParam("code") String code, HttpServletResponse response) {
		System.out.println(code);
		log.debug("code : {}", code);
		GithubToken githubToken = loginService.getAccessToken(code);
		response.setHeader("Authorization", githubToken.getAuthorizationValue());
		return ResponseEntity.ok("logined");
	}
}

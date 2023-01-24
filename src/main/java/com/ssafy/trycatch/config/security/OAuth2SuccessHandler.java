package com.ssafy.trycatch.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final TokenService tokenService;
	private final UserRequestMapper userRequestMapper;
	private final ObjectMapper objectMapper;
	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException,
		ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		User currnetUser = userRequestMapper.toDto(oAuth2User);
		User dbUser = userService.loadUserByUserNodeId(currnetUser.getGithubNodeId());
		if(null == dbUser){
			log.info("신규 User");
			userService.enrollUser(currnetUser);
			dbUser = userService.loadUserByUserNodeId(currnetUser.getGithubNodeId());
		}else{
			log.info("기존 User");
		}

		Token token = tokenService.generateToken(dbUser.getId().toString(), oAuth2User.getAttribute("AC_TOKEN"));
		log.info("{}", token);

		writeTokenResponse(response, token);
	}

	private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
		response.setContentType("text/html;charset=UTF-8");

		response.addHeader("Auth", token.getToken());
		response.addHeader("Refresh", token.getRefreshToken());
		response.setContentType("application/json;charset=UTF-8");

		var writer = response.getWriter();
		writer.println(objectMapper.writeValueAsString(token));
		writer.flush();
	}
}
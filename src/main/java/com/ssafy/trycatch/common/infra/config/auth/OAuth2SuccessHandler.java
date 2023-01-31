package com.ssafy.trycatch.common.infra.config.auth;

import static com.ssafy.trycatch.common.infra.config.jwt.Token.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ssafy.trycatch.common.infra.config.jwt.Token;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final TokenService tokenService;
	private final UserRequestMapper userRequestMapper;
	private final UserRepository userRepository;

	@Value("${settings.login.on_success.redirect_uri}")
	private String redirectUri;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {

		final OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		final String currentUserNodeId = oAuth2User.getAttribute("nodeId");

		// 만약 데이터베이스에 유저가 존재한다면, 해당 객체를 가져오고
		// 만약 존재하지 않는다면, 저장 후 가져오도록 작성됨
		User tempUser = userRepository.findByGithubNodeId(currentUserNodeId)
			.orElse(userRequestMapper.newEntity(oAuth2User));

		tempUser.setActivated(true);
		final User savedUser = userRepository.save(tempUser);


		final Token token = tokenService.generateToken(
			savedUser.getId().toString(),
			oAuth2User.getAttribute("AC_TOKEN")
		);

		log.debug("{}", token);

		// 완료 후 동작
		response.setContentType("text/html;charset=UTF-8");

		response.addHeader(HeaderDefaultTokenAttributeKey, token.getToken());
		response.addHeader(HeaderRefreshTokenAttributeKey, token.getRefreshToken());
		response.setContentType("application/json;charset=UTF-8");

		// 요청하기 전 페이지로 이동
		response.sendRedirect(redirectUri);
	}
}
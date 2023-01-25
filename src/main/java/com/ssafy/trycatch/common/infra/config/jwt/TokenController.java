package com.ssafy.trycatch.common.infra.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.ssafy.trycatch.common.infra.config.jwt.Token.HeaderDefaultTokenAttributeKey;
import static com.ssafy.trycatch.common.infra.config.jwt.Token.HeaderRefreshTokenAttributeKey;

@RequiredArgsConstructor
@RestController
public class TokenController {
	private final TokenService tokenService;

	@GetMapping("/token/expired")
	public String auth() {
		throw new RuntimeException();
	}

	@GetMapping("/token/refresh")
	public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(HeaderRefreshTokenAttributeKey);

		if (token != null && tokenService.verifyToken(token)) {
			String uid = tokenService.getUid(token);

			// for check
			// String accessToken = tokenService.getAccessToken(token);

			Token newToken = tokenService.generateToken(uid, "USER");

			response.addHeader(HeaderDefaultTokenAttributeKey, newToken.getToken());
			response.addHeader(HeaderRefreshTokenAttributeKey, newToken.getRefreshToken());
			response.setContentType("application/json;charset=UTF-8");

			return "HAPPY NEW TOKEN";
		}

		throw new RuntimeException();
	}
}
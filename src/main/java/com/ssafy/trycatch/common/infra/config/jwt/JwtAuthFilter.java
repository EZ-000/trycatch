package com.ssafy.trycatch.common.infra.config.jwt;

import static com.ssafy.trycatch.common.infra.config.jwt.Token.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.trycatch.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	private static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

	@SuppressWarnings("NullableProblems")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader(HeaderDefaultTokenAttributeKey);

		if (null != token && tokenService.verifyToken(token)) {
			// Token 확인 시, 문제가 없다면 Token만 갱신하고 다시 인증할 필요가 없다.
			/**
			 * Need Check
			 * Refresh가 살아있다면 , Access만 재 갱신.
			 * Refresh도 죽었다면, OAuth로 가야한다.
			 */
			Long serviceUserPkId = Long.parseLong(tokenService.getUid(token));
			String githubToken = tokenService.getAccessToken(token);

			Authentication auth = getAuthentication(serviceUserPkId, githubToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}

	public Authentication getAuthentication(Long serviceUserPkId, String credentials) {
		return new UsernamePasswordAuthenticationToken(
				serviceUserPkId,
				credentials,
				List.of(ROLE_USER)
		);
	}
}
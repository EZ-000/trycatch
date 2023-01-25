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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.trycatch.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final TokenService tokenService;

	@SuppressWarnings("NullableProblems")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader(HeaderDefaultTokenAttributeKey);

		if (null != token && tokenService.verifyToken(token)) {
			String nodeId = tokenService.getUid(token);

			User user = User.builder()
				.githubNodeId(nodeId)
				.username("UnKnown")
				.build();

			Authentication auth = getAuthentication(user);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}

	public Authentication getAuthentication(User member) {
		return new UsernamePasswordAuthenticationToken(member, "",
			List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
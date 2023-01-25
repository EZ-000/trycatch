package com.ssafy.trycatch.config.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.ssafy.trycatch.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
	private final TokenService tokenService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
		ServletException {
		String token = ((HttpServletRequest)request).getHeader("Auth");

		if (null != token && tokenService.verifyToken(token)) {
			String nodeId = tokenService.getUid(token);

			User user = User.builder()
				.githubNodeId(nodeId)
				.username("이름").build();

			Authentication auth = getAuthentication(user);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(request, response);
	}

	public Authentication getAuthentication(User member) {
		return new UsernamePasswordAuthenticationToken(member, "",
			Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
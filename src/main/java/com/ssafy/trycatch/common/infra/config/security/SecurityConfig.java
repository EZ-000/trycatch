package com.ssafy.trycatch.common.infra.config.security;

import com.ssafy.trycatch.common.infra.config.auth.CustomOAuth2UserService;
import com.ssafy.trycatch.common.infra.config.auth.OAuth2SuccessHandler;
import com.ssafy.trycatch.common.infra.config.jwt.JwtAuthFilter;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	private final CustomOAuth2UserService oAuth2UserService;
	private final OAuth2SuccessHandler successHandler;
	private final TokenService tokenService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/token/**", "/v1/**").permitAll()
			.anyRequest().authenticated()
			.and()
			// .addFilterBefore(new JwtAuthFilter(tokenService), OAuth2LoginAuthenticationFilter.class)
			.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
			.oauth2Login()
			.successHandler(successHandler)
			.userInfoEndpoint().userService(oAuth2UserService);

		// test
		// http.httpBasic().disable()
		// 	.csrf().disable()
		// 	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// 	.and()
		// 	.authorizeRequests()
		// 	.anyRequest().permitAll();

		return http.build();
	}
}

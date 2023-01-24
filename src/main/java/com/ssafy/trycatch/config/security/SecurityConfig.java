package com.ssafy.trycatch.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

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
			.antMatchers("/token/**","/test").permitAll()
			.anyRequest().authenticated()
			.and()
			.oauth2Login()//.loginPage("/token/expired")
			.successHandler(successHandler)
			.userInfoEndpoint().userService(oAuth2UserService);
		http.addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

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

package com.ssafy.trycatch.common.infra.config.security;

import com.ssafy.trycatch.common.infra.config.auth.CustomOAuth2UserService;
import com.ssafy.trycatch.common.infra.config.auth.OAuth2FailureHandler;
import com.ssafy.trycatch.common.infra.config.auth.OAuth2SuccessHandler;
import com.ssafy.trycatch.common.infra.config.jwt.JwtAuthFilter;
import com.ssafy.trycatch.common.infra.config.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
            .disable()
            .cors()
            .and()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/token/**", "/v1/**", "/docs/**", "/swagger-ui/**")
            .permitAll()
            .anyRequest()
            // 인증이 모두 필요
            .authenticated()
            // 인증을 모두 허용
            //.permitAll()
            .and()
            .oauth2Login()
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .userInfoEndpoint()
            .userService(oAuth2UserService);
        http.addFilterBefore(new JwtAuthFilter(tokenService), OAuth2LoginAuthenticationFilter.class);

        return http.build();
    }
}

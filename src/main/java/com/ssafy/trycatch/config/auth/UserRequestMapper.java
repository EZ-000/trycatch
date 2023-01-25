package com.ssafy.trycatch.config.auth;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.ssafy.trycatch.user.domain.User;

@Component
public class UserRequestMapper {
	public User toDto(OAuth2User oAuth2User) {
		Map<String, Object> attributes = oAuth2User.getAttributes();
		return User.builder()
			.email((String)attributes.get("email"))
			.username((String)attributes.get("name"))
			.githubNodeId((String)attributes.get("nodeId"))
			.gitAddress((String)attributes.get("url"))
			.build();
	}
}
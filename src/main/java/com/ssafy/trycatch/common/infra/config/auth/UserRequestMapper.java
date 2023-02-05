package com.ssafy.trycatch.common.infra.config.auth;

import com.ssafy.trycatch.user.domain.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class UserRequestMapper {
	public User newEntity(OAuth2User oAuth2User) {
		Map<String, Object> attributes = oAuth2User.getAttributes();
		return User.builder()
			.githubNodeId((String)attributes.get("nodeId"))
			.username((String)attributes.get("name"))
			.gitAddress((String)attributes.get("gitHubAddress"))
			.activated(true)
			.email((String)attributes.get("email"))
			.calendarMail(null)
			.confirmationCode(0)
			.company(null)
			.createdAt(LocalDate.now())
			.points(0)
			.imageSrc((String)attributes.get("imageSrc"))
			.build();
	}
}
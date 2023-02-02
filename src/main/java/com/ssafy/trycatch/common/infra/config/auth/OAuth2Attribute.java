package com.ssafy.trycatch.common.infra.config.auth;

import java.util.HashMap;
import java.util.Map;

import com.ssafy.trycatch.common.infra.config.auth.exceptions.NotSupportedOAuth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
final class OAuth2Attribute {
	private Map<String, Object> attributes;
	private String attributeKey;
	private String id;
	private String name;
	private String nodeId;
	private String imageSrc;
	private String gitHubAddress;
	private String email;

	static OAuth2Attribute of(
		String provider,
		String attributeKey,
		Map<String, Object> attributes
	) {
		if (provider.equals("github")) {
			return ofGitHub("node_id", attributes);
		}
		throw new NotSupportedOAuth();
	}

	@SuppressWarnings("SameParameterValue")
	private static OAuth2Attribute ofGitHub(
		String attributeKey,
		Map<String, Object> attributes
	) {
		return OAuth2Attribute.builder()
			.id(attributes.get("id").toString())
			.name((String)attributes.get("login"))
			.nodeId((String)attributes.get("node_id"))
			.imageSrc((String)attributes.get("avatar_url"))
			.gitHubAddress((String)attributes.get("html_url"))
			.email((String)attributes.get("email"))
			.attributes(attributes)
			.attributeKey(attributeKey)
			.build();
	}

	Map<String, Object> convertToMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", attributeKey);
		map.put("key", attributeKey);
		map.put("name", name);
		map.put("nodeId", nodeId);
		map.put("imageSrc", imageSrc);
		map.put("gitHubAddress", gitHubAddress);
		map.put("email", email);

		return map;
	}
}
package com.ssafy.trycatch.config.auth;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
class OAuth2Attribute {
	private Map<String, Object> attributes;
	private String attributeKey;
	private String email;
	private String nodeId;
	private String url;
	private String name;
	private String picture;
	static OAuth2Attribute of(String provider, String attributeKey,
		Map<String, Object> attributes) {

		switch (provider) {
			case "github":
				return ofGitHub("node_id", attributes);
			default:
				throw new RuntimeException();
		}
	}

	private static OAuth2Attribute ofGitHub(String attributeKey,
		Map<String, Object> attributes) {
		return OAuth2Attribute.builder()
			.name((String)attributes.get("name"))
			.email("")
			.picture((String)attributes.get("avatar_url"))
			.nodeId((String)attributes.get("node_id"))
			.url((String)attributes.get("url"))
			.attributes(attributes)
			.attributeKey(attributeKey)
			.build();
	}

	Map<String, Object> convertToMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", attributeKey);
		map.put("key", attributeKey);
		map.put("name", name);
		map.put("email", email);
		map.put("picture", picture);
		map.put("nodeId", nodeId);
		map.put("url", url);

		return map;
	}
}
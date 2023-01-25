package com.ssafy.trycatch.common.infra.config.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {

	public static String HeaderDefaultTokenAttributeKey = "Authorization";
	public static String HeaderRefreshTokenAttributeKey = "Refresh";
	private String token;
	private String refreshToken;

	public Token(String token, String refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}
}

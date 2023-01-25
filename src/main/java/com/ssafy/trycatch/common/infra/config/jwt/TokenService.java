package com.ssafy.trycatch.common.infra.config.jwt;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class TokenService {
	@Value("${spring.jwt.key}")
	private String secretKey;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public Token generateToken(String uid, String token) {
		long tokenPeriod = 1000L * 60L * 10L;
		long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

		Claims claims = Jwts.claims().setSubject(token);
		claims.put("id", uid);

		Date now = new Date();
		return new Token(
			Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenPeriod))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact(),
			Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + refreshPeriod))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact());
	}

	public boolean verifyToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token);
			return claims.getBody()
				.getExpiration()
				.after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public String getUid(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id",String.class);
	}
	public String getAccessToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}
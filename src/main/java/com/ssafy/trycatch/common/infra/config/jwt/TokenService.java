package com.ssafy.trycatch.common.infra.config.jwt;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	@Value("${spring.jwt.key}")
	private String secretKey;

	private Key key;

	@PostConstruct
	protected void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public Token generateToken(String uid, String token) {
		//long tokenPeriod = 1000L * 60L * 10L; // 10 Min
		long tokenPeriod = 1000L * 60L * 60L * 12L; // 12 Hour
		long refreshPeriod = 1000L * 60L * 60L * 24; // 1Day

		Claims claims = Jwts.claims().setSubject(token);
		claims.put("id", uid);

		Date now = new Date();
		return new Token(
			Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenPeriod))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact(),
			Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + refreshPeriod))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact());
	}

	public boolean verifyToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(key).build()
				.parseClaimsJws(token);
			return claims.getBody()
				.getExpiration()
				.after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public String getUid(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id", String.class);
		//return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("id",String.class);
	}

	public String getAccessToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}
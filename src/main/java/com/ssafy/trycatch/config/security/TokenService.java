package com.ssafy.trycatch.config.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class TokenService {
	private String secretKey = "23bb6d094d1c598485c055907272ede0ec9ce8239b3edf662f2df67ee7c76b065003f5ab3a43bc7f4af8a345f064129f64f4354c12ff493a7225cbe00dbd00ce";

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
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenPeriod))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact(),
			Jwts.builder()
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
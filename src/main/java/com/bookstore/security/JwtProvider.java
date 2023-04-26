package com.bookstore.security;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.bookstore.model.dto.Myuser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
	private String jwtSecret = "tronghuong2002@gmial.com";
	
	// thoi gian song cua token tren server
	private int jwtExpiration = 86400;
	
	// khi login se goi create token 
	public String createToken(Authentication authentication) {
		Myuser myUser = (Myuser)authentication.getPrincipal();
		String userName = myUser.getUsername();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpiration*1000);
		
		// Create token
		String token = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date()) // set o thoi diem nao
				.setExpiration(expireDate) // set thoi gian song cua token
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
				
		return token;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}
		catch (SignatureException e) {
			log.error("Invalid JWT signature -> Message: {}", e);
		}
		catch (MalformedJwtException e) {
			log.error("Invalid format Token -> Message: {}", e);
		}
		catch (ExpiredJwtException e) {
			log.error("Expired JWT token -> Message: {}", e);
		}
		catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty -> Message{}", e);
		}
		catch (UnsupportedJwtException e) {
			log.error("Insupported JWT token -> Message: {}", e);
		}
		return false;
	}
	
	public String getUserNameFromToken(String token) {
		String userName = Jwts.parser()
							.setSigningKey(jwtSecret)
							.parseClaimsJws(token)
							.getBody()
							.getSubject();
		return userName;
	}
}

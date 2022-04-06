package hu.futureofmedia.task.contactsapi.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

	@Value("${jwt.public.key}")
	private RSAPublicKey rsaPublicKey;
	@Value("${jwt.private.key}")
	private RSAPrivateKey rsaPrivateKey;

	Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		final ZonedDateTime createdDate = ZonedDateTime.now();
		logger.info("doGenerateToken " + createdDate);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.signWith(SignatureAlgorithm.RS256, rsaPrivateKey)
				.compact();
	}
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(rsaPrivateKey)
				.parseClaimsJws(token);
			return true;
		}catch(UnsupportedJwtException exp) {
			System.out.println("claimsJws argument does not represent Claims JWS" + exp.getMessage());
		}catch(MalformedJwtException exp) {
			System.out.println("claimsJws string is not a valid JWS" + exp.getMessage());
		}catch(SignatureException exp) {
			System.out.println("claimsJws JWS signature validation failed" + exp.getMessage());
		}catch(ExpiredJwtException exp) {
			System.out.println("Claims has an expiration time before the method is invoked" + exp.getMessage());
		}catch(IllegalArgumentException exp) {
			System.out.println("claimsJws string is null or empty or only whitespace" + exp.getMessage());
		}
		return false;
	}
	
	public String getUserNameFromJwtToken(String token) {
		 Claims claims =Jwts.parser()
				   .setSigningKey(rsaPrivateKey)
				   .parseClaimsJws(token)
				   .getBody();
		 return claims.getSubject();
		
	}
}
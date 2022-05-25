package com.tectoro;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.aop.target.dynamic.BeanFactoryRefreshableTargetSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {
	@Value("${app.secret}")
	private String secret;
	
	//1.generate token
	
	public String generateToken(String subject)
	{
		return Jwts.builder().setSubject(subject)
				.setIssuer("TECTORO")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(1)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes())
				.compact();
	}
	//2.read claims
	public Claims getClaims(String token)
	{
		return Jwts.parser().setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
	
	
	//3.read experiration date
	
	public Date getExpDate(String token)
	{
		return getClaims(token).getExpiration();
	}
	
	//4.read subject/username
	
	public String getUsername(String token)
	{
		return getClaims(token).getSubject();
	}
	//5.validate experiration date
	
	public boolean isTokenExp(String token)
	{
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	//6.validate user name in token and database,expdate
	
	public boolean validateToken(String token,String username)
	{
		String tokenUserName = getUsername(token);
		return (username.equals(tokenUserName)&&!isTokenExp(token));
	}
}

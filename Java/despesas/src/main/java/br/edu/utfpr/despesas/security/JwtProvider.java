package br.edu.utfpr.despesas.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	private static final String SECRET_KEY = "pos-java";
	
	public String generateToken(Authentication auth) {
		UserAuthDetails user = (UserAuthDetails) auth.getPrincipal();

		Date agora = new Date();
		Date exp = new Date(agora.getTime() + (60 * 10 * 1000));
		
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(agora)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	public String getUserFromToken(String token) {
		return Jwts.parser() // Inicia o objeto de conversao do token
				.setSigningKey(SECRET_KEY) // Define a chave para descriptografar
				.parseClaimsJws(token) // Executa a descriptografia do token
				.getBody().getSubject(); // Obtém o subject (username) que estava no corpo do token
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token);
		} catch(Exception e) {
			System.out.println("Token inválido! " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
}

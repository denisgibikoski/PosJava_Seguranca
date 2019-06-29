package br.edu.utfpr.despesas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.despesas.entity.dto.LoginFormDTO;
import br.edu.utfpr.despesas.security.JwtProvider;

@RestController("/login")
public class LoginController {

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginFormDTO loginForm) {
		Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginForm.getLogin(), loginForm.getSenha()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = jwtProvider.generateToken(auth);
		
		return ResponseEntity.ok(token);
	}
	
 	
}

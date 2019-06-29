package br.edu.utfpr.exemploauth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		System.out.println(enc.encode("admin"));
		System.out.println(enc.encode("123456"));
	}
	
}

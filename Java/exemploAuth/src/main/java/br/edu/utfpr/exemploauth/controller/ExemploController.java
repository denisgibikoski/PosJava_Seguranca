package br.edu.utfpr.exemploauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExemploController {

	@GetMapping("/publico")
	public String acessoPublico() {
		return "Todo mundo pode acessar!";
	}
	
	@GetMapping("/admin")
	public String acessoAdmin() {
		return "Somente admin pode acessar!";
	}
	
	@GetMapping("/operador")
	public String acessoOperador() {
		return "Operador e admin podem acessar";
	}
	
}

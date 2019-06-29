package br.edu.utfpr.exemploauth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String PWD_ADMIN = "$2a$10$XP6D9tuPfNdkTEzo2Iz16uhy2hOmcx5ifS5lvbA4wlQc25PI2zeoa";
	private static final String PWD_OPERADOR = "$2a$10$YJun5VRlkRiF2bA0eHa2DO3Wx7kLogINbi5wmH03m0NIzvDXQ4WZO";
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Define que o processo e controle de usuários para autenticação será
		// realizado em memória
		auth.inMemoryAuthentication()
			// Define o usuário admin, com senha e os papéis
			.withUser("admin")
				.password(PWD_ADMIN).roles("ADMIN", "USER")
			.and()
			// Define o usuário operador, com senha e seu papel
			.withUser("operador")
				.password(PWD_OPERADOR).roles("USER");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Indica que não haverá controle de sessão para a aplicação
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.httpBasic() // Define que o método de autenticação é Basic Auth
			.and()
			.authorizeRequests() // Define que haverá autenticação de requisição e define as regras
				.antMatchers(HttpMethod.GET, "/operador/**").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
				.antMatchers("/publico/**").permitAll() // Define que essa URL pode
														// ser acessada sem restrição
			.and()
				.cors() // Habilita CORS
			.and()
				.formLogin().disable(); // Desabilita login via form
	}
	
	// Define o bean responsável pela criptografia de senha
	// O Spring irá utilizar o objeto retornado por este método para criptografar a senha
	// enviada pelo usuário e comparar com a senha definida para o usuário no método configure
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
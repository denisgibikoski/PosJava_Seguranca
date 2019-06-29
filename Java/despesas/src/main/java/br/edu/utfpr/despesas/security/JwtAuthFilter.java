package br.edu.utfpr.despesas.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.utfpr.despesas.services.UserDetailsServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		String token = getToken(request);
		
		// Verifica se há um token na requisição e se o token recebido é válido
		if (token != null && jwtProvider.validateToken(token)) {
			String login = jwtProvider.getUserFromToken(token);
			
			// Consulta no banco de dados o usuário logado
			UserDetails userDets = userService.loadUserByUsername(login);
			
			// Cria o objeto de autenticação
			UsernamePasswordAuthenticationToken auth = 
					new UsernamePasswordAuthenticationToken(userDets, 
							null, userDets.getAuthorities());
			auth.setDetails(new WebAuthenticationDetails(request));

			// Autentica o contexto do Spring
			// Permite que os dados de autenticação fiquem disponíveis
			// em outros componentes da aplicação (controller, service, etc)
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}
		
		return null;
	}
	

}

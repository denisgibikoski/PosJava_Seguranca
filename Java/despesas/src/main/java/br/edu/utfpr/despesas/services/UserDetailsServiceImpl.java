package br.edu.utfpr.despesas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.despesas.entity.Usuario;
import br.edu.utfpr.despesas.repository.UsuarioRepository;
import br.edu.utfpr.despesas.security.UserAuthDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioRepository userRep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userRep.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário inválido!"));
		
		return UserAuthDetails.build(usuario);
	}

}

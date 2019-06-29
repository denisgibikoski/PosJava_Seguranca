package br.edu.utfpr.despesas.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.utfpr.despesas.entity.Usuario;

public class UserAuthDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	private String username;
	
	@JsonIgnore
	private String password;
	
	private Integer id;
	
	private Collection<GrantedAuthority> authorities;
	
	public static UserAuthDetails build(Usuario usuario) {
		List<GrantedAuthority> auths = usuario.getPapeis()
				.stream().map(p -> new SimpleGrantedAuthority(p.getPapel()))
				.collect(Collectors.toList());
		
		return new UserAuthDetails(usuario.getLogin(), usuario.getSenha(), 
				usuario.getId(), auths);
	}
	
	public UserAuthDetails(String username, String password, Integer id, Collection<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.id = id;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAuthDetails other = (UserAuthDetails) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

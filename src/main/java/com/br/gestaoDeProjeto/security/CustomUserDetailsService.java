package com.br.gestaoDeProjeto.security;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.gestaoDeProjeto.entity.Usuario;
import com.br.gestaoDeProjeto.service.UsuarioService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername (String email) {
		
		Usuario usuario = getUser(() ->  usuarioService.obterPorEmail(email));
		return usuario;
	}
	
	public UserDetails obterUsuarioPorId (Long id) {
		
		return usuarioService.obterPorId(id).get() ;
	}
	
	private Usuario getUser(Supplier<Optional<Usuario>> supplier){
		
		return supplier.get().orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}

}

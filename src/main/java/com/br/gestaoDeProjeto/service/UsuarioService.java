package com.br.gestaoDeProjeto.service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.gestaoDeProjeto.entity.Usuario;
import com.br.gestaoDeProjeto.repository.UsuariRepository;
import com.br.gestaoDeProjeto.security.JWTService;
import com.br.gestaoDeProjeto.view.model.usuario.LoginResponse;

@Service
public class UsuarioService {
	
	private static final String hederPrefix = "bearer";
	
	@Autowired
	private UsuariRepository usuariRepository ; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	public List<Usuario> obterTodos ( ) {
		
		return usuariRepository.findAll();
	}
	
	public Optional<Usuario> obterPorId (Long id ) {
		
		return usuariRepository.findById(id);
	}
	
	public Optional<Usuario> obterPorEmail (String email ) {
		
		return usuariRepository.findByEmail(email);
	}
	
	public Usuario adicionar(Usuario usuario ) {
		
		usuario.setId(null);
		usuario.setNome(usuario.getNome());
		
		if(obterPorEmail(usuario.getEmail()).isPresent()) {
			
			throw new InputMismatchException("Já existe um usuario cadastrado com email !" + usuario.getEmail());			
		}
		//Codifica e salva a senha do usuario com o encoder
		usuario.setSenha(passwordEncoder.encode((usuario.getSenha())));
		
		return usuariRepository.save(usuario);
		
	}
	
	public LoginResponse logar (String login, String senha) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login, senha,Collections.emptyList()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = hederPrefix + jwtService.gerarToken(authentication);
		
		Usuario usuario = usuariRepository.findByEmail(login).get();
		
		return new LoginResponse(token,usuario);
	}

}

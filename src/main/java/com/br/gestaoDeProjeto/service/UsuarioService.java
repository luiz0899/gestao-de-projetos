package com.br.gestaoDeProjeto.service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.gestaoDeProjeto.entity.Usuario;
import com.br.gestaoDeProjeto.repository.UsuariRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuariRepository usuariRepository ; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

}

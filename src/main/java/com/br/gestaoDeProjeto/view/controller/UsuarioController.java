package com.br.gestaoDeProjeto.view.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.gestaoDeProjeto.entity.Usuario;
import com.br.gestaoDeProjeto.service.UsuarioService;
import com.br.gestaoDeProjeto.view.model.usuario.LoginRequest;
import com.br.gestaoDeProjeto.view.model.usuario.LoginResponse;

@Controller
@CrossOrigin("*")
@RequestMapping("/pi/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<Usuario> obterTodos (){
		
		return usuarioService.obterTodos();
	}
	
	@GetMapping("/{id}")
	public Optional<Usuario> obter(@PathVariable("id") Long id ){
		
		return usuarioService.obterPorId(id);
	}
	
	@PostMapping
	public Usuario adicionar (@RequestBody Usuario usuario) {
		
		return usuarioService.adicionar(usuario);
	}
	
	@PostMapping("/login")
	public LoginResponse login (@RequestBody LoginRequest loginRequest) {
		
		return usuarioService.logar(loginRequest.getEmail(), loginRequest.getSenha());
	}

}

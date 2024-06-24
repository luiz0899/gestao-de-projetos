package com.br.gestaoDeProjeto.view.model.usuario;

import com.br.gestaoDeProjeto.entity.Usuario;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String token ;
	private Usuario usuario ;
	
	public LoginResponse() {
		
	}

	public LoginResponse(String token, Usuario usuario) {
		
		this.token = token;
		this.usuario = usuario;
	
	}

}

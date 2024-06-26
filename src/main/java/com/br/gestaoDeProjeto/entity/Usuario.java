package com.br.gestaoDeProjeto.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "generator_usuario", sequenceName = "sequence_usuario", initialValue = 1, allocationSize = 1)
public class Usuario implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_usuario")
	private Long id ;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	public Usuario(Long id, String nome, String email, String senha) {
		super();
		this.id = id ;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario(String nome, String email, String senha) {
		
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
    public Usuario() {
	}

    //Implementção do usedetails para autenticação.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true ;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true ;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true ;
	}
	
	@Override
	public boolean isEnabled() {
		return true ;
	}


    
	
    
    
	
    

	
	
	

}

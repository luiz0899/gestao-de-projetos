package com.br.gestaoDeProjeto.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.br.gestaoDeProjeto.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
	
	//chave privada de utilizada pelo JWT para codificar e decodificar o token.
	private static final String chavePrivadaJwt = "secretKey";
	
	
	public String gerarToken (Authentication authentication) {
		
		//tempo de expiração do token
		int tempoExpiracao = 50000 ; 
		//aqui estou criando uma data para expirar o token com base no tempo de expiração
		Date dataExpiracao = new Date( new Date().getTime() + tempoExpiracao) ;
		
		Usuario usuario = (Usuario)authentication.getPrincipal();	
		
		//Aqui coleta todos os dados e gera um token jwt.
		return Jwts.builder()
				.setSubject(usuario.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS512, chavePrivadaJwt)
				.compact() ;
	}
	
	public Optional<Long> obterIdDoUsu(String token) {
		
		try {
			
			Claims claims = parse(token).getBody();
			
			// returna o id do token caso encontrado
			return Optional.ofNullable(Long.parseLong(claims.getSubject()));
			
		} catch (Exception e) {
			
			//se não encontrar nada devolve um optional null.
			return Optional.empty() ;

		}
		
	}

	//Metodo que sabe descobrir dentro do token com base na chave privada qual as permições do usuário. 
	private Jws<Claims> parse(String token) {
		return Jwts.parser().setSigningKey(chavePrivadaJwt).parseClaimsJws(token);
	}

}

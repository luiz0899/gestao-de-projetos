package com.br.gestaoDeProjeto.security;

import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	//metodo principal onde toda requisição bate antes de chegar no endpoint.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
			
			// pego o token de dentro da requisição.
			String token = obterToken(request) ;
			//pego o id que esta dentro do token.
			Optional<Long> id = jwtService.obterIdDoUsu(token);
			
			if (id.isPresent()) {
				
				// pego o usuario do dono pelo seu id.
				UserDetails usuario = customUserDetailsService.obterUsuarioPorId(id.get());
				//Aqui verificamos as autenticações do usuario e as permissões.
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
				//Mudando a autenticação para a propria requisição.
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//repasso a autenticação para o contexto e o a partir de agora o spring toma conta do resto.
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			//metodo padrão para filtar as regras do usuario.
			filterChain.doFilter(request, response) ;
			
	}
		
	private String obterToken(HttpServletRequest request) {
			
		String token = request.getHeader("Authorization");
			
		if(!StringUtils.hasText(token)) {
			return null;
		}
		
		return token.substring(7);
	}
}

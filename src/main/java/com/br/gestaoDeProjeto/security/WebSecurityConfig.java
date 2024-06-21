package com.br.gestaoDeProjeto.security;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity // Aqui informo que a classe é uma classe de segurança do webSecurity.
public class WebSecurityConfig extends WebSecurityConfiguration {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService ;
	@Autowired
	private JWTAuthenticationFilter authenticationFilter;
	
	
	/*metodo desenvolvido para instânciar objeto que sabe devolver o nosso padrão de codificação.
	  Isso não tem nada a ver com JWT
	  Aqui será usado para codificar a senha do usuario gerando um hash.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Metodo padrão para configurar o nosso custom com o nosso metodo de codificar senha
	@Autowired
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		
		authenticationManagerBuilder
			.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	//metodo padrão para conseguirmos trabalhar com a autenticação no login.
	
	/*metodo depreciado,
	 * public AuthenticationManager authenticationManagerBean() throws Exception  {
		
		return super.authenticationManagerBean();
	}*/
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	 }
	
	protected void configure(HttpSecurity http) throws Exception {
		
		//parte padrão da configuração
		 http
          .cors().and().csrf().disable()
          .exceptionHandling()
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .authorizeRequests()
          
          /*Daqui pra baixo é onde sempre sera modificado para fazer as validações,
            aqui vamos informar as rotas que não vão precisar de autenticação.
          */
             .antMatchers(HttpMethod.POST, "/api/usuarios", "/api/usuarios/login")
             .permitAll()//informas que todos podem acessar.
             .anyRequest().authenticated();//digo que as demais requisições devem ser autenticadas.
             	http.addFilterBefore(JWTAuthenticationFilter, UsarnamePasswordAuthenticationFilter.class);
		         
     
		 return http.build();
	}

}

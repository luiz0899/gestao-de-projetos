package com.br.gestaoDeProjeto.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.gestaoDeProjeto.entity.Usuario;
import com.br.gestaoDeProjeto.view.model.usuario.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca o usuário pelo email (supondo que o email seja o username)
        Optional<Usuario> optionalUsuario = usuarioService.obterPorEmail(username);
        if (!optionalUsuario.isPresent()) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }

        Usuario usuario = optionalUsuario.get();

        Set<GrantedAuthority> authorities = new HashSet<>();
        // Adiciona as roles do usuário como GrantedAuthority (pode ser ajustado conforme a estrutura do seu usuário)
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Exemplo de role

        // Crie o UserDetails usando o email como username, senha do usuário e authorities
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(authorities)
                .build();
    }
}

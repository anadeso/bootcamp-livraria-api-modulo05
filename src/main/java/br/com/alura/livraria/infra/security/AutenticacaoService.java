package br.com.alura.livraria.infra.security;

import br.com.alura.livraria.dto.LoginFormDto;
import br.com.alura.livraria.repositories.UsuarioRepository;
import br.com.alura.livraria.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public String autenticar(LoginFormDto dto) {
        //Autenticar, quando valido, gera token e devolver token
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
        authentication = authenticationManager.authenticate(authentication);

        return tokenService.gerarToken(authentication);
    }
}

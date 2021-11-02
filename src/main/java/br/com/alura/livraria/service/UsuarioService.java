package br.com.alura.livraria.service;

import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UsuarioDto cadastrar(UsuarioFormDto usuarioFormDto) {
        Usuario usuario = modelMapper.map(usuarioFormDto, Usuario.class);

        String senha = new Random().nextInt(999999) + "";
        usuario.setSenha(senha);

        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDto.class);
    }
}

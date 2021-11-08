package br.com.alura.livraria.service;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;

import br.com.alura.livraria.entities.Autor;
import br.com.alura.livraria.entities.Livro;

import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.repositories.AutorRepository;
import br.com.alura.livraria.repositories.LivroRepository;
import br.com.alura.livraria.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional(readOnly = true)
    public Page<LivroDto> listar(Pageable paginacao, Usuario usuario) {
        Page<Livro> livros = livroRepository.findAllByUsuario(paginacao, usuario);
        return livros.map(x -> modelMapper.map(x, LivroDto.class));
    }

    @Transactional
    public LivroDto cadastrar(LivroFormDto livroFormDto, Usuario logado) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Livro livro = modelMapper.map(livroFormDto, Livro.class);
        Long autorId = livroFormDto.getAutorId();
        Long usuarioId = livroFormDto.getUsuarioId();

        try {
            Autor autor = autorRepository.getById(autorId);
            Usuario usuario = usuarioRepository.getById(usuarioId);

            if (!usuario.equals(logado)) {
                return lancarErroAcessoNegado();
            }

            livro.setId(null);
            livro.setAutor(autor);
            livro.setUsuario(usuario);

            livroRepository.save(livro);

            return modelMapper.map(livro, LivroDto.class);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Autor inexistente!");
        }
    }

    @Transactional
    public LivroDto atualizar(AtualizacaoLivroFormDto dto, Usuario logado) {
        Livro livro = livroRepository.getById(dto.getId());

        if (!livro.pertenceAoUsuario(logado)) {
            return lancarErroAcessoNegado();
        }

        livro.atualizarInformacoes(dto.getTitulo(), dto.getDataLancamento(), dto.getNumeroPagina());
        return modelMapper.map(livro, LivroDto.class);
    }

    @Transactional
    public void remover(Long id, Usuario logado) {
        Livro livro = livroRepository.getById(id);

        if (!livro.pertenceAoUsuario(logado)) {
            lancarErroAcessoNegado();
        }

        livroRepository.deleteById(id);
    }

    public LivroDto listarPorId(Long id, Usuario logado) {
        Livro livro = livroRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        if (!livro.pertenceAoUsuario(logado)) {
            lancarErroAcessoNegado();
        }

        return modelMapper.map(livro, LivroDto.class);
    }

    private LivroDto lancarErroAcessoNegado() {
        throw new AccessDeniedException("Acesso negado!");
    }
}

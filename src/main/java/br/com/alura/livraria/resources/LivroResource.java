package br.com.alura.livraria.resources;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.entities.Usuario;
import br.com.alura.livraria.service.AutorService;
import br.com.alura.livraria.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/livros")
public class LivroResource {

    @Autowired
    private LivroService livroService;

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ResponseEntity<Page<LivroDto>> listar(Pageable pageable, @AuthenticationPrincipal Usuario logado) {
        Page<LivroDto> list = livroService.listar(pageable, logado);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<LivroDto> cadastrar(@RequestBody @Valid LivroFormDto livroFormDto,  @AuthenticationPrincipal Usuario logado) {
        LivroDto livroDto = livroService.cadastrar(livroFormDto, logado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(livroDto.getId()).toUri();
        return ResponseEntity.created(uri).body(livroDto);
    }

    @PutMapping
    public ResponseEntity<LivroDto> atualizar(@RequestBody @Valid AtualizacaoLivroFormDto dto, @AuthenticationPrincipal Usuario logado) {
        LivroDto atualizada = livroService.atualizar(dto, logado);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LivroDto> delete(@PathVariable @NotNull Long id, @AuthenticationPrincipal Usuario logado) {
        livroService.remover(id, logado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDto> listaPorId(@PathVariable @NotNull Long id, @AuthenticationPrincipal Usuario logado) {
        LivroDto dto = livroService.listarPorId(id, logado);
        return ResponseEntity.ok(dto);
    }

}

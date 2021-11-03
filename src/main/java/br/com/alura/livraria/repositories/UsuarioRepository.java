package br.com.alura.livraria.repositories;

import br.com.alura.livraria.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);
}

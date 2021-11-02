package br.com.alura.livraria.repositories;

import br.com.alura.livraria.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>  {
}

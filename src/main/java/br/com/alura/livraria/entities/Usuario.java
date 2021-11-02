package br.com.alura.livraria.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"senha"})
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;
}

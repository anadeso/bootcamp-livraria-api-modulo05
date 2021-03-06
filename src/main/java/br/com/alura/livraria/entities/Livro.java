package br.com.alura.livraria.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @Column(name = "data")
    private LocalDate dataLancamento;

    @Column(name = "numpagina")
    private Integer numeroPagina;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro(String titulo, LocalDate dataLancamento, Integer numeroPagina, Autor autor) {
        this.titulo = titulo;
        this.dataLancamento = dataLancamento;
        this.numeroPagina = numeroPagina;
        this.autor = autor;
    }

    public void atualizarInformacoes(String titulo, LocalDate dataLancamento, int numeroPagina) {
        this.titulo = titulo;
        this.dataLancamento = dataLancamento;
        this.numeroPagina = numeroPagina;
    }
}

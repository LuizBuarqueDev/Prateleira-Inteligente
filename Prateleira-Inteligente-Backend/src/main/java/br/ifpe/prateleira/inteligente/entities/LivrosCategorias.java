package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;
@Entity
@Table(name= "LivrosCategorias")
public class LivrosCategorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idLivros")
    private Livros livro;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public LivrosCategorias(Long id, Livros livro, Categoria categoria) {
        this.id = id;
        this.livro = livro;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Livros getLivro() { return livro; }
    public void setLivro(Livros livro) { this.livro = livro; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}

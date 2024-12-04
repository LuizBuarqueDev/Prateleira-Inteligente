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
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public LivrosCategorias(Long id, Livro livro, Categoria categoria) {
        this.id = id;
        this.livro = livro;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}

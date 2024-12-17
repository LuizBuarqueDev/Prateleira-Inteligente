package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private java.sql.Date anoPublicacao;
    private String descricao;
    private String editora;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<UsuarioLivro> usuarioLivro = new ArrayList<>();

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<LivrosCategorias> livrosCategorias = new ArrayList<>();

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comentario> comentarios = new ArrayList<>();


    public Livro() {}

    public Livro(String titulo, String autor, Date anoPublicacao, String descricao, String editora, List<UsuarioLivro> usuarioLivro, List<LivrosCategorias> livrosCategorias, List<Comentario> comentarios) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.descricao = descricao;
        this.editora = editora;
        this.usuarioLivro = usuarioLivro;
        this.livrosCategorias = livrosCategorias;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Date anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public List<UsuarioLivro> getUsuarioLivro() {
        return usuarioLivro;
    }

    public void setUsuarioLivro(List<UsuarioLivro> usuarioLivro) {
        this.usuarioLivro = usuarioLivro;
    }

    public List<LivrosCategorias> getLivrosCategorias() {
        return livrosCategorias;
    }

    public void setLivrosCategorias(List<LivrosCategorias> livrosCategorias) {
        this.livrosCategorias = livrosCategorias;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
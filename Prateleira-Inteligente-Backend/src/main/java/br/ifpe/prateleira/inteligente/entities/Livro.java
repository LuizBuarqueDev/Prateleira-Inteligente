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
    private List<EstanteLivro> estanteLivros = new ArrayList<>();

    public Livro() {}

    public Livro(Long id, String titulo, String autor, Date anoPublicacao, String descricao, String editora) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.descricao = descricao;
        this.editora = editora;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public java.sql.Date getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(java.sql.Date anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public List<EstanteLivro> getEstanteLivros() { return estanteLivros; }
    public void setEstanteLivros(List<EstanteLivro> estanteLivros) { this.estanteLivros = estanteLivros; }
}
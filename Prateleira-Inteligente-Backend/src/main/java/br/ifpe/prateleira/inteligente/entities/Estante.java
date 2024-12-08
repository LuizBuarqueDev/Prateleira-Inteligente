package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Estantes")
public class Estante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstante;

    private String descricao;

    @OneToOne(mappedBy = "estante", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToMany(mappedBy = "estante", cascade = CascadeType.ALL)
    private List<Livro> livros = new ArrayList<>();

    public Estante() {
    }

    public Estante(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdEstante() {
        return idEstante;
    }

    public void setIdEstante(Long idEstante) {
        this.idEstante = idEstante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}

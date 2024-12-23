package br.ifpe.prateleira.inteligente.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<LivrosCategorias> livrosCategorias = new ArrayList<>();


    public Categoria() {}

    public Categoria(List<LivrosCategorias> livrosCategorias, String nome) {
        this.livrosCategorias = livrosCategorias;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<LivrosCategorias> getLivrosCategorias() {
        return livrosCategorias;
    }

    public void setLivrosCategorias(List<LivrosCategorias> livrosCategorias) {
        this.livrosCategorias = livrosCategorias;
    }
}
package com.prateleira_inteligente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Categorias")
public class Categoria extends BaseEntity {

    private String nome;

    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.ALL)
    private List<Livro> livros = new ArrayList<>();
}

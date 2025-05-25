package com.prateleira_inteligente;

import com.prateleira_inteligente.entities.Autor;
import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.services.AutorService;
import com.prateleira_inteligente.services.CategoriaService;
import com.prateleira_inteligente.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mock {

    private final LivroService livroService;
    private final CategoriaService categoriaService;
    private final AutorService autorService;

    @Transactional
    public void mocking() {

        List<Autor> autores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Autor autor = new Autor();
            autor.setNome("Autor " + (i + 1));
            autores.add(autorService.save(autor));
        }

        Categoria terror = criarOuObterCategoria("Terror");
        Categoria romance = criarOuObterCategoria("Romance");
        Categoria drama = criarOuObterCategoria("Drama");
        Categoria acao = criarOuObterCategoria("Ação");

        criarLivro(
                "Bicho Feio",
                LocalDate.of(2013, 10, 1),
                "Uma análise filosófica sobre a estética do absurdo.",
                "https://mega.ibxk.com.br/2013/10/materias/1861434302315547.jpg?ims=fit-in/800x500",
                "Estética Urbana Press",
                autores.get(0),
                drama
        );

        criarLivro(
                "Tun Tun Tun Sahur",
                LocalDate.of(2021, 6, 15),
                "Crônicas sonoras de madrugadas e tradições esquecidas.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFwZF4X0FTSL2QlYj-j9k7epjNYh_Z83JQlQ&s",
                "Editora Alvorada",
                autores.get(1),
                romance
        );

        criarLivro(
                "Bombardiro Crocodilo",
                LocalDate.of(2022, 3, 10),
                "Um thriller militar animal onde o réptil é a arma secreta.",
                "https://cdn-0001.qstv.on.epicgames.com/DeWFZjGciBtKpknWZf/image/landscape_comp.jpeg",
                "Ação & Ficção Ltda",
                autores.get(2),
                acao
        );

        criarLivroComMultiplasCategorias(
                "Tralalero Tralala",
                LocalDate.of(2023, 9, 5),
                "Uma ópera nonsense entre dois mundos barulhentos.",
                "https://i.redd.it/bombardino-crocodilo-vs-tralalero-tralala-tung-tung-tung-v0-6ti8d8wlrnse1.jpg?width=1600&format=pjpg&auto=webp&s=024b4f6e4264d1b0373860672365359c5977bd25",
                "Caos Poético Publishing",
                autores.get(3),
                drama, romance
        );

        criarLivro(
                "Nosferato",
                LocalDate.of(1922, 3, 4),
                "Uma releitura obscura e satírica do clássico vampiro que virou meme.",
                "https://wallpapers.com/images/hd/weird-profile-pictures-8zdeg7mx9z0jz80j.jpg",
                "Sombras & Risos Editora",
                autores.get(4),
                terror
        );
    }

    private void criarLivro(String titulo, LocalDate anoPublicacao, String descricao,
                            String capa, String editora, Autor autor, Categoria categoria) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setDescricao(descricao);
        livro.setCapa(capa);
        livro.setEditora(editora);
        livro.setAutor(autor);

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria);
        livro.setCategorias(categorias);

        livroService.save(livro);
    }

    private void criarLivroComMultiplasCategorias(String titulo, LocalDate anoPublicacao, String descricao,
                                                  String capa, String editora, Autor autor, Categoria... categorias) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setDescricao(descricao);
        livro.setCapa(capa);
        livro.setEditora(editora);
        livro.setAutor(autor);

        List<Categoria> listaCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            listaCategorias.add(categoria);
        }
        livro.setCategorias(listaCategorias);

        livroService.save(livro);
    }

    private Categoria criarOuObterCategoria(String nome) {

        Categoria existente = categoriaService.findByNome(nome);
        if (existente != null) {
            return existente;
        }

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        return categoriaService.save(categoria);
    }

    private void criarLivro(String titulo, LocalDate anoPublicacao, String descricao,
                            String capa, String editora, Categoria categoria) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setDescricao(descricao);
        livro.setCapa(capa);
        livro.setEditora(editora);

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria);
        livro.setCategorias(categorias);

        livroService.save(livro);
    }

    private void criarLivroComMultiplasCategorias(String titulo, LocalDate anoPublicacao, String descricao,
                                                  String capa, String editora, Categoria... categorias) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setDescricao(descricao);
        livro.setCapa(capa);
        livro.setEditora(editora);

        List<Categoria> listaCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            listaCategorias.add(categoria);
        }
        livro.setCategorias(listaCategorias);

        livroService.save(livro);
    }
}
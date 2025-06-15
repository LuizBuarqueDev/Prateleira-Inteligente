package com.prateleira_inteligente.services;


import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prateleira_inteligente.services.PrateleiraService;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LivroService implements IService<Livro> {

    private final LivroRepository livroRepository;
    private final ComentarioService comentarioService;


    @Override
    @Transactional
    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }

    @Override
    @Transactional
    //TODO: Implementar o update
    public Livro update(Long id, Livro livroAtualizado) {
        Livro livroExistente = getById(id);

        // Atualiza campos básicos
        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livroExistente.setDescricao(livroAtualizado.getDescricao());
        livroExistente.setEditora(livroAtualizado.getEditora());

        // Atualiza o autor
        livroExistente.setAutor(livroAtualizado.getAutor());

        // Atualiza categorias (remove antigas e adiciona novas)
        livroExistente.getCategorias().clear();
        if (livroAtualizado.getCategorias() != null) {
            livroExistente.getCategorias().addAll(livroAtualizado.getCategorias());
        }

        // Atualiza usuariosLivros
        livroExistente.getUsuariosLivros().clear();
        if (livroAtualizado.getUsuariosLivros() != null) {
            for (UsuarioLivro usuarioLivro : livroAtualizado.getUsuariosLivros()) {
                usuarioLivro.setLivro(livroExistente); // garante referência reversa correta
                livroExistente.getUsuariosLivros().add(usuarioLivro);
            }
        }

        return livroRepository.save(livroExistente);
    }

    @Override
    @Transactional(readOnly = true)
    public Livro getById(Long id) {
        return livroRepository.findById(id).orElseThrow(()-> new RuntimeException("Livro não encontrado: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        livroRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Livro> findAllById(List<Long> ids) {
        return livroRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<Livro> buscar (String busca) {
        return livroRepository.findByTermo(busca);
    }

    @Transactional(readOnly = true)
    public List<Livro> findByCategoriaId(Long categoriaId) {
        return livroRepository.findByCategoriaId(categoriaId);
    }

    @Transactional(readOnly = true)
    public List<Livro> filterLivros(Double minRating, String sortBy) {
        List<Livro> livros = livroRepository.findAll();

        // Filtro por avaliação mínima
        PrateleiraService prateleiraService = null;
        if (minRating != null) {
            livros = livros.stream()
                    .filter(l -> {
                        Double media = prateleiraService.calcularMediaAvaliacoes(l.getId());
                        return media != null && media >= minRating;
                    })
                    .collect(Collectors.toList());
        }

        // Ordenação
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "rating":
                    livros.sort((l1, l2) -> {
                        assert prateleiraService != null;
                        Double media1 = prateleiraService.calcularMediaAvaliacoes(l1.getId());
                        Double media2 = prateleiraService.calcularMediaAvaliacoes(l2.getId());
                        return media2.compareTo(media1);
                    });
                    break;
                case "title":
                    livros.sort((l1, l2) -> l1.getTitulo().compareToIgnoreCase(l2.getTitulo()));
                    break;
                case "date":
                    livros.sort((l1, l2) -> l2.getAnoPublicacao().compareTo(l1.getAnoPublicacao()));
                    break;
            }
        }

        return livros;
    }
    @Transactional(readOnly = true)
    public List<Livro> findSimilarBooks(Long idLivro) {
        Livro livroAtual = getById(idLivro);

        return livroRepository.findAll().stream()
                .filter(l -> !l.getId().equals(idLivro))
                .filter(l -> l.getCategorias().stream()
                        .anyMatch(c -> livroAtual.getCategorias().contains(c)))
                .sorted((l1, l2) -> {
                    long commonCats1 = l1.getCategorias().stream()
                            .filter(c -> livroAtual.getCategorias().contains(c))
                            .count();
                    long commonCats2 = l2.getCategorias().stream()
                            .filter(c -> livroAtual.getCategorias().contains(c))
                            .count();
                    return Long.compare(commonCats2, commonCats1);
                })
                .limit(4)
                .collect(Collectors.toList());
    }

}
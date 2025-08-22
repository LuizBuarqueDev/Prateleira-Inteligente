package com.prateleira_inteligente.services;


import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.entities.UsuarioLivro;
import com.prateleira_inteligente.repositories.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LivroService implements IService<Livro> {

    private final LivroRepository livroRepository;

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
    public List<Livro> filterLivros(Double minRating, String sortBy){
        List<Livro> livros = livroRepository.findAll();

        // Filtro por avaliação mínima
        PrateleiraService prateleiraService = null;
        if (minRating != null) {
            livros = livros.stream()
                    .filter(l -> {
                        assert false;
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
                        assert false;
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
        Set<Long> adicionados = new HashSet<>();
        adicionados.add(idLivro); // não incluir o próprio livro

        List<Livro> resultado = new ArrayList<>();

        // Adiciona livros por categoria
        resultado.addAll(findBooksByCategoryRandom(livroAtual, adicionados));

        // Adiciona livros por autor
        resultado.addAll(findBooksByAuthorRandom(livroAtual, adicionados));

        return resultado;
    }

    // ==== Função: livros por categoria aleatórios ====
    private List<Livro> findBooksByCategoryRandom(Livro livroAtual, Set<Long> adicionados) {
        Map<Categoria, List<Livro>> livrosPorCategoria = livroRepository.findAll().stream()
                .filter(l -> !adicionados.contains(l.getId()))
                .filter(l -> l.getCategorias().stream()
                        .anyMatch(c -> livroAtual.getCategorias().contains(c)))
                .collect(Collectors.groupingBy(
                        l -> {
                            return l.getCategorias().stream()
                                    .filter(c -> livroAtual.getCategorias().contains(c))
                                    .findFirst().get();
                        }
                ));

        List<Livro> resultado = new ArrayList<>();
        for (List<Livro> lista : livrosPorCategoria.values()) {
            // Embaralha os livros da categoria
            Collections.shuffle(lista);
            int count = 0;
            for (Livro l : lista) {
                if (!adicionados.contains(l.getId()) && count < 2) {
                    resultado.add(l);
                    adicionados.add(l.getId());
                    count++;
                }
            }
        }
        return resultado;
    }

    private List<Livro> findBooksByAuthorRandom(Livro livroAtual, Set<Long> adicionados) {
        List<Livro> livrosPorAutor = livroRepository.findAll().stream()
                .filter(l -> !adicionados.contains(l.getId()))
                .filter(l -> l.getAutor().equals(livroAtual.getAutor()))
                .collect(Collectors.toList());

        Collections.shuffle(livrosPorAutor);
        List<Livro> resultado = livrosPorAutor.stream()
                .limit(2)
                .collect(Collectors.toList());

        resultado.forEach(l -> adicionados.add(l.getId()));
        return resultado;
    }
}
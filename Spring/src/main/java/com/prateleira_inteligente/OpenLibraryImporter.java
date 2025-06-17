package com.prateleira_inteligente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prateleira_inteligente.entities.Autor;
import com.prateleira_inteligente.entities.Categoria;
import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.services.AutorService;
import com.prateleira_inteligente.services.CategoriaService;
import com.prateleira_inteligente.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenLibraryImporter {

    private final LivroService livroService;
    private final AutorService autorService;
    private final CategoriaService categoriaService;

    @Transactional
    public void importarLivrosAutomaticamente() {
        int maxResults = 100; // <-- define aqui a quantidade que você quer
        String query = "b";   // query genérica para buscar livros variados

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        int page = 1;
        int totalInseridos = 0;

        while (totalInseridos < maxResults) {
            try {
                String url = "https://openlibrary.org/search.json?q=" + query + "&page=" + page;
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) break;

                JsonNode root = mapper.readTree(response.body());
                JsonNode docs = root.get("docs");
                if (docs == null || !docs.isArray() || docs.size() == 0) break;

                for (JsonNode doc : docs) {
                    if (totalInseridos >= maxResults) break;

                    Livro livro = new Livro();
                    livro.setTitulo(doc.has("title") ? doc.get("title").asText() : "Sem título");

                    if (doc.has("first_publish_year")) {
                        int year = doc.get("first_publish_year").asInt();
                        livro.setAnoPublicacao(LocalDate.of(year, 1, 1));
                    }

                    if (doc.has("first_sentence") && doc.get("first_sentence").isArray()) {
                        livro.setDescricao(doc.get("first_sentence").get(0).asText());
                    } else {
                        livro.setDescricao("Sem descrição.");
                    }

                    if (doc.has("cover_i")) {
                        String coverId = doc.get("cover_i").asText();
                        livro.setCapa("https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg");
                    }

                    if (doc.has("publisher") && doc.get("publisher").isArray()) {
                        livro.setEditora(doc.get("publisher").get(0).asText());
                    }

                    // Autor
                    if (doc.has("author_name") && doc.get("author_name").isArray()) {
                        String nomeAutor = doc.get("author_name").get(0).asText();
                        Autor autor = autorService.findByname(nomeAutor);
                        if (autor == null) {
                            autor = new Autor();
                            autor.setNome(nomeAutor);
                            autor = autorService.save(autor);
                        }
                        livro.setAutor(autor);
                    }

                    // Categoria
                    if (doc.has("subject") && doc.get("subject").isArray()) {
                        String nomeCategoria = doc.get("subject").get(0).asText();
                        Categoria categoria = categoriaService.findByNome(nomeCategoria);
                        if (categoria == null) {
                            categoria = new Categoria();
                            categoria.setNome(nomeCategoria);
                            categoria = categoriaService.save(categoria);
                        }
                        List<Categoria> categorias = new ArrayList<>();
                        categorias.add(categoria);
                        livro.setCategorias(categorias);
                    }

                    livroService.save(livro);
                    totalInseridos++;
                }

                page++;

            } catch (Exception e) {
                System.out.println("Erro ao importar livros: " + e.getMessage());
                break;
            }
        }

        System.out.println("Importação concluída: " + totalInseridos + " livros inseridos.");
    }
}
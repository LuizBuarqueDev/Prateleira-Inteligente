package com.prateleira_inteligente;

import com.prateleira_inteligente.entities.Livro;
import com.prateleira_inteligente.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Mock {

    private final LivroService service;

    public void mocking () {
        Livro livro2 = new Livro();
        livro2.setTitulo("Bicho Feio");
        livro2.setAnoPublicacao(LocalDate.of(2013, 10, 1));
        livro2.setDescricao("Uma análise filosófica sobre a estética do absurdo.");
        livro2.setCapa("https://mega.ibxk.com.br/2013/10/materias/1861434302315547.jpg?ims=fit-in/800x500");
        livro2.setEditora("Estética Urbana Press");
        service.save(livro2);

        Livro livro3 = new Livro();
        livro3.setTitulo("Tun Tun Tun Sahur");
        livro3.setAnoPublicacao(LocalDate.of(2021, 6, 15));
        livro3.setDescricao("Crônicas sonoras de madrugadas e tradições esquecidas.");
        livro3.setCapa("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSFwZF4X0FTSL2QlYj-j9k7epjNYh_Z83JQlQ&s");
        livro3.setEditora("Editora Alvorada");
        service.save(livro3);

        Livro livro4 = new Livro();
        livro4.setTitulo("Bombardiro Crocodilo");
        livro4.setAnoPublicacao(LocalDate.of(2022, 3, 10));
        livro4.setDescricao("Um thriller militar animal onde o réptil é a arma secreta.");
        livro4.setCapa("https://cdn-0001.qstv.on.epicgames.com/DeWFZjGciBtKpknWZf/image/landscape_comp.jpeg");
        livro4.setEditora("Ação & Ficção Ltda");
        service.save(livro4);

        Livro livro5 = new Livro();
        livro5.setTitulo("Tralalero Tralala");
        livro5.setAnoPublicacao(LocalDate.of(2023, 9, 5));
        livro5.setDescricao("Uma ópera nonsense entre dois mundos barulhentos.");
        livro5.setCapa("https://i.redd.it/bombardino-crocodilo-vs-tralalero-tralala-tung-tung-tung-v0-6ti8d8wlrnse1.jpg?width=1600&format=pjpg&auto=webp&s=024b4f6e4264d1b0373860672365359c5977bd25");
        livro5.setEditora("Caos Poético Publishing");
        service.save(livro5);

        Livro livro6 = new Livro();
        livro6.setTitulo("Nosferato");
        livro6.setAnoPublicacao(LocalDate.of(1922, 3, 4));
        livro6.setDescricao("Uma releitura obscura e satírica do clássico vampiro que virou meme.");
        livro6.setCapa("https://wallpapers.com/images/hd/weird-profile-pictures-8zdeg7mx9z0jz80j.jpg");
        livro6.setEditora("Sombras & Risos Editora");

        service.save(livro6);
    }
}

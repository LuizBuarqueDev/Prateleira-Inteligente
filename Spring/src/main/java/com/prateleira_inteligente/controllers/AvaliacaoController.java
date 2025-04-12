package com.prateleira_inteligente.controllers;

import com.prateleira_inteligente.dto.AvaliacaoDTO;
import com.prateleira_inteligente.entities.Avaliacao;
import com.prateleira_inteligente.mappers.IMapper;
import com.prateleira_inteligente.services.IService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoController extends AbsGenericController<Avaliacao, AvaliacaoDTO> {

    public AvaliacaoController(IService<Avaliacao> service, IMapper<Avaliacao, AvaliacaoDTO> mapper) {
        super(service, mapper);
    }
}
package com.prateleira_inteligente.mappers;

public interface IMapper <T, DTO> {
    T toEntity(DTO dto);
    DTO toDTO(T entity);
}

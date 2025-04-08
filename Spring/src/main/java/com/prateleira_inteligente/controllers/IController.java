package com.prateleira_inteligente.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IController<T, Long> {
    ResponseEntity<T> create(T dto);
    ResponseEntity<T> findById(Long id);
    ResponseEntity<T> update(Long id, T dto);
    ResponseEntity<Void> delete(Long id);
    ResponseEntity<List<T>> findAllByIds(List<Long> ids);
    ResponseEntity<List<T>> findAll();
}
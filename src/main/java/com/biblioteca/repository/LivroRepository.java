package com.biblioteca.repository;

import com.biblioteca.model.Livro;

import java.util.Optional;
import java.util.UUID;

public interface LivroRepository {

    Optional<Livro> buscarPorId(UUID id);

    void salvar(Livro livro);
}

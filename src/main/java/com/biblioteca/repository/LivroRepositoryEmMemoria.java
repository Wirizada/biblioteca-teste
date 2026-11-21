package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import com.biblioteca.model.enums.Status;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LivroRepositoryEmMemoria implements LivroRepository{

    Map<UUID, Livro> listaDeLivros = new HashMap<>();

    @Override
    public Optional<Livro> buscarPorId(UUID id) {
        return Optional.ofNullable(listaDeLivros.get(id));
    }

    @Override
    public void salvar(Livro livro) {
        listaDeLivros.put(livro.getId(), livro);
    }
}

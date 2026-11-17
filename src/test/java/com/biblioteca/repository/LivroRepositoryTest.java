package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import com.biblioteca.model.enums.Status;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LivroRepositoryTest {

    @Test
    void salvarEBuscarOMesmoLivro(){
        LivroRepository repository = new LivroRepositoryEmMemoria();

        UUID id = UUID.randomUUID();
        Livro livro = new Livro(id, "o mais rico", Status.DISPONIVEL, "joao das dores");

        repository.salvar(livro);
        Optional<Livro> resultado = repository.buscarPorId(livro.getId());

        assertEquals(Optional.of(livro), resultado);
    }

    @Test
    void buscarIdInexistenteDevolveCaixinhaVazia() {
        LivroRepository repository = new LivroRepositoryEmMemoria();

        Optional<Livro> resultado = repository.buscarPorId(UUID.randomUUID());

        assertEquals(Optional.empty(), resultado);
    }
}
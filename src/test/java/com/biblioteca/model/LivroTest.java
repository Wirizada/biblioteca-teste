package com.biblioteca.model;

import com.biblioteca.model.enums.Status;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivroTest {

    @Test
    void livrosComOMesmoIdSaoIguaisMesmoComStatusDiferente() {
        UUID id = UUID.randomUUID();
        Livro disponivel = new Livro(id, "o mais rico", Status.DISPONIVEL, "autor");
        Livro indisponivel = new Livro(id, "o mais rico", Status.EMPRESTADO, "autor");

        assertEquals(disponivel, indisponivel);
    }
}

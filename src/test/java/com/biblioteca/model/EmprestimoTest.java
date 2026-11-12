package com.biblioteca.model;

import com.biblioteca.model.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    @Test
    void criarEmprestimo() {

        Livro l = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");
        Membro m = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");

        LocalDate hoje = LocalDate.of(2026, 11, 12);

        Emprestimo emprestimo = Emprestimo.criarEmprestimo(l, m, hoje);

        assertEquals(Status.EMPRESTADO, l.getStatus());
        assertEquals(LocalDate.of(2026, 11, 26), emprestimo.getDataDevolucao());
    }
}
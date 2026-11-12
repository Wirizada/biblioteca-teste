package com.biblioteca.model;

import com.biblioteca.model.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    @Test
    void criarEmprestimo() {
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");

        LocalDate hoje = LocalDate.of(2026, 11, 12);

        Emprestimo emprestimo = Emprestimo.criarEmprestimo(livro, membro, hoje);

        assertEquals(Status.EMPRESTADO, livro.getStatus());
        assertEquals(LocalDate.of(2026, 11, 26), emprestimo.getDataDevolucao());
    }

    @Test
    void devolverEmprestimo() {
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.EMPRESTADO, "Autor Teste");
        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, null, null, null);

        emprestimo.devolver();

        assertEquals(Status.DISPONIVEL, livro.getStatus());
    }
}
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

    @Test
    void naoEmprestarLivroIndisponivel() {

        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");

        LocalDate hoje = LocalDate.of(2026, 11, 12);

        Emprestimo.criarEmprestimo(livro, membro, hoje);

        assertThrows(IllegalStateException.class, () -> Emprestimo.criarEmprestimo(livro, membro, hoje));
        assertEquals(Status.EMPRESTADO, livro.getStatus());
    }

    @Test
    void membroNaoPodeTerMaisDeTresLivros() {
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        LocalDate hoje = LocalDate.of(2026, 11, 12);

        for (int i = 0; i < 3; i++) {
            Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");
            Emprestimo.criarEmprestimo(livro, membro, hoje);
        }
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste dois", Status.DISPONIVEL, "Autor Teste dois");
        assertThrows(IllegalStateException.class, () -> Emprestimo.criarEmprestimo(livro, membro, hoje));
    }

    @Test
    void recusaPorLimiteNaoMarcaLivroComoEmprestado() {
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");

        for (int i = 0; i < 3; i++) {
            Livro livro1 = new Livro(UUID.randomUUID(), "Livro Teste dois", Status.DISPONIVEL, "Autor Teste dois");
            Emprestimo.criarEmprestimo(livro1, membro, hoje);
        }

        assertThrows(IllegalStateException.class, () -> Emprestimo.criarEmprestimo(livro, membro, hoje));
        assertEquals(Status.DISPONIVEL, livro.getStatus());
    }

    @Test
    void tentativasRecusadasNaoConsomemVagas() {
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        LocalDate hoje = LocalDate.of(2026, 11, 12);


        for (int i = 0; i < 3; i++) {
            Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.EMPRESTADO, "Autor Teste");
            assertThrows(IllegalStateException.class, () -> Emprestimo.criarEmprestimo(livro, membro, hoje));
        }

        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");
        Emprestimo.criarEmprestimo(livro, membro, hoje);
        assertEquals(true, membro.temVaga());
    }
}
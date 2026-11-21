package com.biblioteca.model;

import com.biblioteca.model.enums.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

    @Test
    void multaDeDoisReaisPorDiaDeAtraso() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);

        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), null, null, hoje, dataDevolucao);

        LocalDate dataDevolvida = LocalDate.of(2026, 11, 29);

        BigDecimal multa = emprestimo.calcularMulta(dataDevolvida);

        assertEquals(new BigDecimal("6.00"), multa);
    }

    @Test
    void devolvidoNoPrazo() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);

        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), null, null, hoje, dataDevolucao);

        LocalDate dataDevolvida = LocalDate.of(2026, 11, 26);

        BigDecimal multa = emprestimo.calcularMulta(dataDevolvida);

        assertEquals(new BigDecimal("0.00"), multa);
    }

    @Test
    void devolvidoAntesDoPrazo() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);

        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), null, null, hoje, dataDevolucao);

        LocalDate dataDevolvida = LocalDate.of(2026, 11, 24);

        BigDecimal multa = emprestimo.calcularMulta(dataDevolvida);

        assertEquals(new BigDecimal("0.00"), multa);
    }

    @Test
    void renovarEmprestimo() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.EMPRESTADO, "Autor Teste");

        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);

        emprestimo.renovar(LocalDate.of(2026, 11, 26));

        assertEquals(LocalDate.of(2026, 12, 10), emprestimo.getDataDevolucao());
        assertEquals(Status.EMPRESTADO, livro.getStatus());
    }

    @Test
    void NaoRenovarPorPrazoExcedido() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.EMPRESTADO, "Autor Teste");

        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);

        assertThrows(IllegalStateException.class, () -> emprestimo.renovar(LocalDate.of(2026, 11, 27)));
    }

    @Test
    void NaoRenovarMaisQueUmaVez() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.EMPRESTADO, "Autor Teste");
        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);
        emprestimo.renovar(LocalDate.of(2026, 11, 26));


        assertThrows(IllegalStateException.class, () -> emprestimo.renovar(LocalDate.of(2026, 11, 30)));
        assertEquals(1, emprestimo.getVezesRenovadas());
    }

    @Test
    void naoTrocaMudaDataAoRecusarRenovacao() {
        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.EMPRESTADO, "Autor Teste");
        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);
        emprestimo.renovar(LocalDate.of(2026, 11, 26));

        assertThrows(IllegalStateException.class, () -> emprestimo.renovar(LocalDate.of(2026, 12, 8)));
        assertEquals(LocalDate.of(2026,12,10), emprestimo.getDataDevolucao());
    }
}
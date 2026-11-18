package com.biblioteca.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Emprestimo {

    private UUID id;
    private Livro livro;
    private Membro membro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private static final BigDecimal MULTA_POR_DIA = new BigDecimal("2.00");
    private int vezesRenovadas = 0;
    private static final int LIMITE_RENOVACAO = 1;

    public Emprestimo(UUID id, Livro livro, Membro membro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getVezesRenovadas() {
        return vezesRenovadas;
    }

    public static Emprestimo criarEmprestimo(Livro livro, Membro membro, LocalDate hoje) {

        if (!livro.estaDisponivel() || !membro.temVaga()) {
            throw new IllegalStateException("Não é possivel realizar o emprestimo");
        }

        LocalDate dataDevolucao = hoje.plusDays(14);
        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);

        membro.registrarEmprestimo(emprestimo);
        livro.emprestar();

        return emprestimo;
    }

    public BigDecimal calcularMulta(LocalDate dataDevolvida) {
        long dias = ChronoUnit.DAYS.between(dataDevolucao, dataDevolvida);

        if (dias < 0) {
            return new BigDecimal("0.00");
        }

        return BigDecimal.valueOf(dias).multiply(MULTA_POR_DIA);
    }

    public void renovar (LocalDate hoje){
        if (hoje.isAfter(dataDevolucao) || vezesRenovadas == LIMITE_RENOVACAO){
            throw new IllegalStateException("Não é permitido renovar o emprestimo");
        }
        vezesRenovadas++;
        setDataDevolucao(dataDevolucao.plusDays(14));
    }

    public void devolver() {
        livro.devolver();
    }
}

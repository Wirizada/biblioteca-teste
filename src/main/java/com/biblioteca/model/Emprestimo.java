package com.biblioteca.model;

import com.biblioteca.model.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

public class Emprestimo {

    private UUID id;
    private Livro livro;
    private Membro membro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

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

    public static Emprestimo criarEmprestimo(Livro livro, Membro membro, LocalDate hoje) {
        livro.emprestar();
        LocalDate dataDevolucao = hoje.plusDays(14);

        return new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);
    }

    public void devolver(){
        livro.devolver();
    }
}

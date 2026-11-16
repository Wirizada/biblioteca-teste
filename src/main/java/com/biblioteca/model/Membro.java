package com.biblioteca.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Membro {

    private UUID id;
    private String nome;
    private String cpf;
    private List<Emprestimo> livrosEmprestados = new ArrayList<>();
    private static final int LIMITE = 3;

    public Membro(UUID id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Emprestimo> getLivrosEmprestados() {
        return List.copyOf(livrosEmprestados);
    }

    public void registrarEmprestimo(Emprestimo emprestimo) {
        if (livrosEmprestados.size() >= LIMITE) {
            throw new IllegalStateException("Membro não pode emprestar mais que 3 livros");
        }
        livrosEmprestados.add(emprestimo);
    }

    public boolean temVaga() {
        return livrosEmprestados.size() < LIMITE;
    }
}

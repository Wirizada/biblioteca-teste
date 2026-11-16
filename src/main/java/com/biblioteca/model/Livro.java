package com.biblioteca.model;

import com.biblioteca.model.enums.Status;

import java.util.UUID;

public class Livro {

    private UUID id;
    private String titulo;
    private Status status;
    private String autor;

    public Livro(UUID id, String titulo, Status status, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.status = status;
        this.autor = autor;
    }

    public UUID getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getAutor() {
        return autor;
    }

    private void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    private void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void emprestar() {
        if (this.status != Status.DISPONIVEL) {
            throw new IllegalStateException("Não é possível criar um empréstimo para um livro que não está disponível.");
        }
        this.status = Status.EMPRESTADO;
    }

    public boolean estaDisponivel() {
        return status == Status.DISPONIVEL;
    }

    public void devolver() {
        this.status = Status.DISPONIVEL;
    }
}

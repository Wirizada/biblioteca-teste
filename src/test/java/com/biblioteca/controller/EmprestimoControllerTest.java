package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import com.biblioteca.model.enums.Status;
import com.biblioteca.repository.LivroRepositoryEmMemoria;
import com.biblioteca.repository.MembroRepositoryEmMemoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmprestimoControllerTest {

    @Autowired
    private EmprestimoController emprestimoController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepositoryEmMemoria livroRepositoryEmMemoria;

    @Autowired
    private MembroRepositoryEmMemoria membroRepositoryEmMemoria;

    @Test
    void membroInexistenteDa404() throws Exception {
        UUID id = UUID.randomUUID();
        Livro livroTeste = new Livro(id, "Teste", Status.DISPONIVEL, "Teste");

        livroRepositoryEmMemoria.salvar(livroTeste);

        mockMvc.perform(post("/emprestimos").contentType("application/json")
                .content("{\"livroId\":\"" + livroTeste.getId() + "\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void livroInexistenteDa404() throws Exception {
        UUID id = UUID.randomUUID();
        Membro membroTeste = new Membro(id, "João", "12345678901");

        membroRepositoryEmMemoria.salvar(membroTeste);

        mockMvc.perform(post("/emprestimos").contentType("application/json")
                .content("{\"membroId\":\"" + membroTeste.getId() + "\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void realizarEmprestimo() throws Exception {
        UUID id = UUID.randomUUID();
        Membro membroTeste = new Membro(id, "João", "12345678901");
        Livro livroTeste = new Livro(id, "Teste", Status.DISPONIVEL, "Teste");

        livroRepositoryEmMemoria.salvar(livroTeste);
        membroRepositoryEmMemoria.salvar(membroTeste);

        mockMvc.perform(post("/emprestimos").contentType("application/json")
                .content("{\"membroId\":\"" + membroTeste.getId() + "\",\"livroId\":\"" +
                    livroTeste.getId() + "\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.emprestimoId").exists())
            .andExpect(jsonPath("$.dataDevolucao").exists());
    }

    @Test
    void naoRealizarEmprestimoDeLivroEmprestado() throws Exception {
        UUID id = UUID.randomUUID();
        Membro membroTeste = new Membro(id, "João", "12345678901");
        Livro livroTeste = new Livro(id, "Teste", Status.EMPRESTADO, "Teste");

        livroRepositoryEmMemoria.salvar(livroTeste);
        membroRepositoryEmMemoria.salvar(membroTeste);

        mockMvc.perform(post("/emprestimos").contentType("application/json")
                .content("{\"membroId\":\"" + membroTeste.getId() + "\",\"livroId\":\"" +
                    livroTeste.getId() + "\"}"))
            .andExpect(status().isConflict())
            .andExpect(status().reason("Livro não está disponivel para emprestimo."));
    }
}

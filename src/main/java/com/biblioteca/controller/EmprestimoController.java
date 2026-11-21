package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import com.biblioteca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@Controller
public class EmprestimoController {
    @Autowired
    private LivroRepositoryEmMemoria livroRepository;

    @Autowired
    private MembroRepositoryEmMemoria membroRepository;

    @PostMapping("/emprestimos")
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoResponseDTO realizarEmprestimo(@RequestBody EmprestimoRequestDTO request) {
        Livro livroId = livroRepository.buscarPorId(request.livroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro inexistente"));

        Membro membroId = membroRepository.buscarPorId(request.membroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro inexistente"));

        try {
            Emprestimo response = Emprestimo.criarEmprestimo(livroId, membroId, LocalDate.now());
            return new EmprestimoResponseDTO(response.getId(), response.getDataDevolucao());
        } catch (IllegalStateException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }
    }
}

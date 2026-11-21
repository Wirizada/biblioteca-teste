package com.biblioteca.repository;

import java.time.LocalDate;
import java.util.UUID;

public record EmprestimoResponseDTO(UUID emprestimoId, LocalDate dataDevolucao) {}

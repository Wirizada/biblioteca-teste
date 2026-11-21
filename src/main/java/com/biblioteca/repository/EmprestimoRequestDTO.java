package com.biblioteca.repository;

import java.util.UUID;

public record EmprestimoRequestDTO(UUID livroId, UUID membroId) {
}

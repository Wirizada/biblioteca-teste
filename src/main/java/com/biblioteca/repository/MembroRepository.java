package com.biblioteca.repository;


import com.biblioteca.model.Membro;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface MembroRepository {

    Optional<Membro> buscarPorId(UUID id);

    void salvar(Membro membro);
}

package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MembroRepositoryEmMemoria implements MembroRepository{

    Map<UUID, Membro> listaDeMembros = new HashMap<>();

    @Override
    public Optional<Membro> buscarPorId(UUID id) {
        return Optional.ofNullable(listaDeMembros.get(id));
    }

    @Override
    public void salvar(Membro membro) {
        listaDeMembros.put(membro.getId(), membro);
    }
}

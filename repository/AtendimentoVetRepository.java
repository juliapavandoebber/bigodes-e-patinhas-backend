package com.bigodesepatinhas.backend.repository;

import com.bigodesepatinhas.backend.model.AtendimentoVet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendimentoVetRepository extends JpaRepository<AtendimentoVet, Long> {
    List<AtendimentoVet> findByPetIdPet(Long idPet);
}
package com.bigodesepatinhas.backend.repository;

import com.bigodesepatinhas.backend.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByPetTutorIdTutor(Long idTutor);
    List<Agendamento> findByPetIdPet(Long idPet);
}
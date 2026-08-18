package com.bigodesepatinhas.backend.repository;

import com.bigodesepatinhas.backend.model.DisponibilidadeVet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisponibilidadeVetRepository extends JpaRepository<DisponibilidadeVet, Long> {
    
    // Buscar todas as disponibilidades de um veterinário em uma data específica
    List<DisponibilidadeVet> findByVeterinarioIdVeterinarioAndData(Long idVeterinario, LocalDate data);

    // Buscar apenas os horários livres/habilitados para os clientes marcarem
    List<DisponibilidadeVet> findByVeterinarioIdVeterinarioAndDataAndDisponivelTrue(Long idVeterinario, LocalDate data);

    // Localizar registro exato para atualizar status
    Optional<DisponibilidadeVet> findByVeterinarioIdVeterinarioAndDataAndHorario(Long idVeterinario, LocalDate data, LocalTime horario);
}
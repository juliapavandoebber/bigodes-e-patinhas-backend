package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.dto.AgendamentoRequestDTO;
import com.bigodesepatinhas.backend.model.Agendamento;
import com.bigodesepatinhas.backend.model.Pet;
import com.bigodesepatinhas.backend.model.Veterinario;
import com.bigodesepatinhas.backend.repository.AgendamentoRepository;
import com.bigodesepatinhas.backend.repository.PetRepository;
import com.bigodesepatinhas.backend.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @PostMapping
    public ResponseEntity<?> agendarAtendimento(@RequestBody AgendamentoRequestDTO dto) {
        Pet pet = petRepository.findById(dto.getIdPet()).orElse(null);
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pet não encontrado.");
        }

        Veterinario vet = veterinarioRepository.findById(dto.getIdVeterinario()).orElse(null);
        if (vet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Veterinário não encontrado.");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setDataAgendamento(dto.getData());
        agendamento.setHorarioAgendamento(dto.getHorario());
        agendamento.setPet(pet);
        agendamento.setVeterinario(vet);
        agendamento.setIdServico(dto.getIdServico());
        agendamento.setStatusAgendamento("AGENDADO");

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/tutor/{idTutor}")
    public ResponseEntity<List<Agendamento>> consultarAgendamentosPorTutor(@PathVariable Long idTutor) {
        return ResponseEntity.ok(agendamentoRepository.findByPetTutorIdTutor(idTutor));
    }
}
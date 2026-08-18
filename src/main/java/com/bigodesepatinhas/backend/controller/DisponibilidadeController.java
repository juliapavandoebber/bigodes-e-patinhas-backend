package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.dto.DisponibilidadeDTO;
import com.bigodesepatinhas.backend.model.DisponibilidadeVet;
import com.bigodesepatinhas.backend.model.Veterinario;
import com.bigodesepatinhas.backend.repository.DisponibilidadeVetRepository;
import com.bigodesepatinhas.backend.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeVetRepository disponibilidadeRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    // 1. O veterinário clica nos horários e salva (habilita/desabilita)
    @PostMapping
    public ResponseEntity<?> salvarOuAtualizarDisponibilidade(@RequestBody DisponibilidadeDTO dto) {
        Veterinario vet = veterinarioRepository.findById(dto.getIdVeterinario()).orElse(null);
        if (vet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Veterinário não encontrado.");
        }

        Optional<DisponibilidadeVet> existente = disponibilidadeRepository
                .findByVeterinarioIdVeterinarioAndDataAndHorario(dto.getIdVeterinario(), dto.getData(), dto.getHorario());

        DisponibilidadeVet registro = existente.orElse(new DisponibilidadeVet());
        registro.setVeterinario(vet);
        registro.setData(dto.getData());
        registro.setHorario(dto.getHorario());
        registro.setDisponivel(dto.getDisponivel());

        DisponibilidadeVet salvo = disponibilidadeRepository.save(registro);
        return ResponseEntity.ok(salvo);
    }

    // 2. O modal do veterinário abre e busca o estado de todos os horários dele no dia selecionado
    @GetMapping("/vet/{idVeterinario}")
    public ResponseEntity<List<DisponibilidadeVet>> listarPorVeterinarioEData(
            @PathVariable Long idVeterinario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(disponibilidadeRepository.findByVeterinarioIdVeterinarioAndData(idVeterinario, data));
    }

    // 3. O cliente/atendente abre o calendário para agendar e só vê os horários disponíveis (true)
    @GetMapping("/livres")
    public ResponseEntity<List<DisponibilidadeVet>> listarHorariosLivres(
            @RequestParam Long idVeterinario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(disponibilidadeRepository.findByVeterinarioIdVeterinarioAndDataAndDisponivelTrue(idVeterinario, data));
    }
}
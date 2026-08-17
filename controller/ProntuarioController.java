package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.model.AtendimentoVet;
import com.bigodesepatinhas.backend.repository.AtendimentoVetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prontuarios")
public class ProntuarioController {

    @Autowired
    private AtendimentoVetRepository atendimentoVetRepository;

    @GetMapping("/pet/{idPet}")
    public ResponseEntity<List<AtendimentoVet>> consultarProntuario(@PathVariable Long idPet) {
        List<AtendimentoVet> historico = atendimentoVetRepository.findByPetIdPet(idPet);
        return ResponseEntity.ok(historico);
    }
}
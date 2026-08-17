package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.model.Veterinario;
import com.bigodesepatinhas.backend.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @GetMapping
    public ResponseEntity<List<Veterinario>> listarVeterinarios() {
        return ResponseEntity.ok(veterinarioRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Veterinario> cadastrarVeterinario(@RequestBody Veterinario veterinario) {
        return ResponseEntity.ok(veterinarioRepository.save(veterinario));
    }
}
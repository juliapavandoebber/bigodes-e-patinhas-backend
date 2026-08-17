package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.model.Tutor;
import com.bigodesepatinhas.backend.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @PostMapping
    public ResponseEntity<Tutor> cadastrarTutor(@RequestBody Tutor tutor) {
        Tutor novoTutor = tutorRepository.save(tutor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTutor);
    }

    @GetMapping
    public ResponseEntity<List<Tutor>> listarTutores() {
        return ResponseEntity.ok(tutorRepository.findAll());
    }
}
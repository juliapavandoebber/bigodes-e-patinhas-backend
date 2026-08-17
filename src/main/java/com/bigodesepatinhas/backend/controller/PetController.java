package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.dto.PetRequestDTO;
import com.bigodesepatinhas.backend.model.Pet;
import com.bigodesepatinhas.backend.model.Tutor;
import com.bigodesepatinhas.backend.repository.PetRepository;
import com.bigodesepatinhas.backend.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @PostMapping
    public ResponseEntity<?> cadastrarPet(@RequestBody PetRequestDTO dto) {
        Tutor tutor = tutorRepository.findById(dto.getIdTutor()).orElse(null);
        if (tutor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tutor não encontrado com o ID fornecido.");
        }

        Pet pet = new Pet();
        pet.setNomePet(dto.getNomePet());
        pet.setIdade(dto.getIdade());
        pet.setPeso(dto.getPeso());
        pet.setIdRaca(dto.getIdRaca());
        pet.setIdGenero(dto.getIdGenero());
        pet.setTutor(tutor);

        Pet petSalvo = petRepository.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listarPets() {
        return ResponseEntity.ok(petRepository.findAll());
    }
}
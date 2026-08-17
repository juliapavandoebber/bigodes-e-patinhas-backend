package com.bigodesepatinhas.backend.repository;

import com.bigodesepatinhas.backend.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByCpf(String cpf);
}
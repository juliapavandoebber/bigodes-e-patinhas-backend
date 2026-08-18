package com.bigodesepatinhas.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/veterinarios")
@CrossOrigin(origins = "*")
public class VeterinarioController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarVeterinarios() {
        String sql = """
            SELECT 
                "id_Veterinario" AS id, 
                "nome_Veterinaria" AS nome, 
                "crmv", 
                "descricao_Veterinario" AS descricao
            FROM "Veterinario"
            ORDER BY "nome_Veterinaria" ASC
        """;
        List<Map<String, Object>> vets = jdbcTemplate.queryForList(sql);
        return ResponseEntity.ok(vets);
    }
}
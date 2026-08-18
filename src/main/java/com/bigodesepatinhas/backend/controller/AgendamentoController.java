package com.bigodesepatinhas.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/historico-tutor")
    public ResponseEntity<List<Map<String, Object>>> listarHistoricoTutor(
            @RequestParam(value = "idTutor", defaultValue = "1") Long idTutor) {

        String sql = """
            SELECT 
                a.id_agendamento AS id,
                TO_CHAR(a.data_agendamento, 'DD/MM/YYYY') AS data,
                TO_CHAR(a.horario_agendamento, 'HH24:MI') AS horario,
                COALESCE(p."nome_Pet", 'Pet') AS "nomePet",
                COALESCE(v."nome_Veterinaria", 'Veterinário Não Informado') AS "nomeVeterinario",
                COALESCE(s."nome_Servico", 'Consulta Geral') AS "nomeServico",
                COALESCE(a.status_agendamento, 'Agendado') AS status
            FROM "Agendamento" a
            LEFT JOIN "Pet" p ON p."id_Pet" = a.id_pet
            LEFT JOIN "Cliente" c ON c."id_Cliente" = p."id_Cliente"
            LEFT JOIN "Veterinario" v ON v."id_Veterinario" = a.id_veterinario
            LEFT JOIN "Servico" s ON s."id_Servico" = a.id_servico
            WHERE (c."id_Cliente" = ? OR a.id_pet = ?)
            ORDER BY a.data_agendamento DESC, a.horario_agendamento DESC
        """;

        List<Map<String, Object>> historico = jdbcTemplate.queryForList(sql, idTutor, idTutor);
        return ResponseEntity.ok(historico);
    }
}
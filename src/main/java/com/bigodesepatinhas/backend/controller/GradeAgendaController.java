package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.dto.GradeAgendaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grade-agenda")
@CrossOrigin(origins = "*")
public class GradeAgendaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarHorariosDoDia(
            @RequestParam(value = "idVeterinario", required = true) Object idVeterinario,
            @RequestParam(value = "data", required = true) String data) {

        Long idVet = Long.valueOf(idVeterinario.toString());

        String sql = """
            SELECT 
                g."id_Grade" AS id,
                g."id_Veterinario" AS "idVeterinario",
                TO_CHAR(g."horario_Agenda", 'HH24:MI') AS horario,
                g."horario_Agenda" AS "dataCompleta",
                g."is_Ativo" AS "isAtivo",
                EXISTS (
                    SELECT 1 FROM "Agendamento" a 
                    WHERE a.id_veterinario = g."id_Veterinario"
                      AND a.data_agendamento = CAST(g."horario_Agenda" AS DATE)
                      AND a.horario_agendamento = CAST(g."horario_Agenda" AS TIME)
                ) AS agendado
            FROM "Grade_Agenda" g
            WHERE g."id_Veterinario" = ?
              AND CAST(g."horario_Agenda" AS DATE) = CAST(? AS DATE)
            ORDER BY g."horario_Agenda" ASC
        """;

        List<Map<String, Object>> grade = jdbcTemplate.queryForList(sql, idVet, data);
        return ResponseEntity.ok(grade);
    }

    @PostMapping("/salvar-dia")
    public ResponseEntity<?> salvarHorariosDoDia(@RequestBody GradeAgendaDTO dto) {
        try {
            if (dto.getIdVeterinario() == null || dto.getData() == null) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Veterinário e data são obrigatórios."));
            }

            Long idVet = Long.valueOf(dto.getIdVeterinario().toString());
            String dataStr = dto.getData();

            // 1. Remove os horários configurados para este dia
            String deleteSql = """
                DELETE FROM "Grade_Agenda"
                WHERE "id_Veterinario" = ?
                  AND CAST("horario_Agenda" AS DATE) = CAST(? AS DATE)
            """;
            jdbcTemplate.update(deleteSql, idVet, dataStr);

            // 2. Insere os novos horários selecionados pelo veterinário
            if (dto.getHorarios() != null && !dto.getHorarios().isEmpty()) {
                String insertSql = """
                    INSERT INTO "Grade_Agenda" ("id_Veterinario", "horario_Agenda", "is_Ativo")
                    VALUES (?, CAST(? AS TIMESTAMP), true)
                """;

                for (String h : dto.getHorarios()) {
                    String horaLimpa = h.trim();
                    if (horaLimpa.length() == 5) {
                        horaLimpa += ":00";
                    }
                    String dataHoraTexto = dataStr + " " + horaLimpa;
                    jdbcTemplate.update(insertSql, idVet, dataHoraTexto);
                }
            }

            return ResponseEntity.ok(Map.of("mensagem", "Disponibilidade salva com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }
}
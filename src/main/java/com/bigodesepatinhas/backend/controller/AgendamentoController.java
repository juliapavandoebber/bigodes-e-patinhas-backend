package com.bigodesepatinhas.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarAgendamentos(
            @RequestParam(value = "idVeterinario", required = false) Long idVeterinario,
            @RequestParam(value = "data", required = false) String data) {

        String sql = """
            SELECT 
                a.id_agendamento AS id,
                a.id_veterinario AS "idVeterinario",
                COALESCE(v."nome_Veterinaria", 'Veterinário') AS "nomeVeterinario",
                TO_CHAR(a.data_agendamento, 'DD/MM/YYYY') AS data,
                TO_CHAR(a.horario_agendamento, 'HH24:MI') AS horario,
                a.status_agendamento AS status
            FROM "Agendamento" a
            LEFT JOIN "Veterinario" v ON v."id_Veterinario" = a.id_veterinario
            WHERE (? IS NULL OR a.id_veterinario = ?)
              AND (? IS NULL OR a.data_agendamento = CAST(? AS DATE))
            ORDER BY a.data_agendamento DESC, a.horario_agendamento DESC
        """;

        List<Map<String, Object>> lista = jdbcTemplate.queryForList(
                sql,
                idVeterinario, idVeterinario,
                data, data
        );

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody Map<String, Object> body) {
        try {
            Long idVeterinario = Long.valueOf(body.get("idVeterinario").toString());
            Long idPet = body.get("idPet") != null ? Long.valueOf(body.get("idPet").toString()) : 1L;
            Long idServico = body.get("idServico") != null ? Long.valueOf(body.get("idServico").toString()) : 1L;

            String dataHoraStr = (String) body.get("dataHora"); // "YYYY-MM-DD HH:mm:ss"
            String[] partes = dataHoraStr.split(" ");
            LocalDate data = LocalDate.parse(partes[0]);
            LocalTime hora = LocalTime.parse(partes[1]);

            // 1. Verifica se já existe agendamento neste dia e horário
            String sqlCheck = """
                SELECT COUNT(*) FROM "Agendamento"
                WHERE id_veterinario = ?
                  AND data_agendamento = ?
                  AND horario_agendamento = ?
            """;
            Integer count = jdbcTemplate.queryForObject(
                    sqlCheck,
                    Integer.class,
                    idVeterinario,
                    Date.valueOf(data),
                    Time.valueOf(hora)
            );

            if (count != null && count > 0) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Este horário já foi reservado."));
            }

            // 2. Insere o agendamento no banco
            String sqlInsert = """
                INSERT INTO "Agendamento" (
                    id_veterinario, 
                    id_pet, 
                    id_servico, 
                    data_agendamento, 
                    horario_agendamento, 
                    status_agendamento
                ) VALUES (?, ?, ?, ?, ?, 'Agendado')
            """;
            jdbcTemplate.update(
                    sqlInsert,
                    idVeterinario,
                    idPet,
                    idServico,
                    Date.valueOf(data),
                    Time.valueOf(hora)
            );

            return ResponseEntity.ok(Map.of("mensagem", "Agendamento realizado com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }
}
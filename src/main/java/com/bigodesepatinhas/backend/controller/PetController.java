package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.dto.PetCadastroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarTodos() {
        String sql = """
            SELECT 
                p."id_Pet" AS "id",
                p."nome_Pet" AS "nome",
                e."nome_Especie" AS "especie",
                r."nome_Raca" AS "raca",
                p."idade_Pet" AS "idade",
                p."peso_Pet" AS "peso",
                g."nome_Genero" AS "genero",
                p."id_Tutor" AS "idTutor",
                t."nome_Tutor" AS "nomeTutor",
                t."nome_Tutor" AS "tutorNome",
                t."telefone_Tutor" AS "telefoneTutor",
                t."CPF" AS "cpfTutor"
            FROM "Pet" p
            LEFT JOIN "Raca" r ON p."id_Raca" = r."id_Raca"
            LEFT JOIN "Especie" e ON r."id_Especie" = e."id_Especie"
            LEFT JOIN "Genero" g ON p."id_Genero" = g."id_Genero"
            LEFT JOIN "Tutor" t ON p."id_Tutor" = t."id_Tutor"
            ORDER BY p."id_Pet" DESC
        """;
        return ResponseEntity.ok(jdbcTemplate.queryForList(sql));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        String sql = """
            SELECT 
                p."id_Pet" AS "id",
                p."nome_Pet" AS "nome",
                e."nome_Especie" AS "especie",
                r."nome_Raca" AS "raca",
                p."idade_Pet" AS "idade",
                p."peso_Pet" AS "peso",
                g."nome_Genero" AS "genero",
                p."id_Tutor" AS "idTutor",
                t."nome_Tutor" AS "nomeTutor",
                t."nome_Tutor" AS "tutorNome",
                t."telefone_Tutor" AS "telefoneTutor",
                t."CPF" AS "cpfTutor"
            FROM "Pet" p
            LEFT JOIN "Raca" r ON p."id_Raca" = r."id_Raca"
            LEFT JOIN "Especie" e ON r."id_Especie" = e."id_Especie"
            LEFT JOIN "Genero" g ON p."id_Genero" = g."id_Genero"
            LEFT JOIN "Tutor" t ON p."id_Tutor" = t."id_Tutor"
            WHERE p."id_Pet" = ?
        """;
        List<Map<String, Object>> pets = jdbcTemplate.queryForList(sql, id);
        if (pets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pets.get(0));
    }

    @GetMapping("/tutor/{idTutor}")
    public ResponseEntity<List<Map<String, Object>>> listarPorTutor(@PathVariable Long idTutor) {
        String sql = """
            SELECT 
                p."id_Pet" AS "id",
                p."nome_Pet" AS "nome",
                e."nome_Especie" AS "especie",
                r."nome_Raca" AS "raca",
                p."idade_Pet" AS "idade",
                p."peso_Pet" AS "peso",
                g."nome_Genero" AS "genero",
                p."id_Tutor" AS "idTutor",
                t."nome_Tutor" AS "nomeTutor",
                t."nome_Tutor" AS "tutorNome"
            FROM "Pet" p
            LEFT JOIN "Raca" r ON p."id_Raca" = r."id_Raca"
            LEFT JOIN "Especie" e ON r."id_Especie" = e."id_Especie"
            LEFT JOIN "Genero" g ON p."id_Genero" = g."id_Genero"
            LEFT JOIN "Tutor" t ON p."id_Tutor" = t."id_Tutor"
            WHERE p."id_Tutor" = ?
            ORDER BY p."id_Pet" DESC
        """;
        return ResponseEntity.ok(jdbcTemplate.queryForList(sql, idTutor));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody PetCadastroDTO dto) {
        try {
            // 1. Resolução do Gênero
            Integer idGenero = null;
            if (dto.getGeneroNome() != null && !dto.getGeneroNome().isBlank()) {
                List<Integer> listG = jdbcTemplate.query(
                    "SELECT \"id_Genero\" FROM \"Genero\" WHERE LOWER(\"nome_Genero\") = LOWER(?) LIMIT 1",
                    (rs, rowNum) -> rs.getInt("id_Genero"),
                    dto.getGeneroNome().trim()
                );
                idGenero = listG.isEmpty() ? null : listG.get(0);
            }

            // 2. Resolução da Espécie
            Integer idEspecie = null;
            if (dto.getEspecieNome() != null && !dto.getEspecieNome().isBlank()) {
                List<Integer> listE = jdbcTemplate.query(
                    "SELECT \"id_Especie\" FROM \"Especie\" WHERE LOWER(\"nome_Especie\") = LOWER(?) LIMIT 1",
                    (rs, rowNum) -> rs.getInt("id_Especie"),
                    dto.getEspecieNome().trim()
                );
                if (!listE.isEmpty()) {
                    idEspecie = listE.get(0);
                } else {
                    Integer nextEspecieId = jdbcTemplate.queryForObject(
                        "SELECT COALESCE(MAX(\"id_Especie\"), 0) + 1 FROM \"Especie\"",
                        Integer.class
                    );
                    idEspecie = jdbcTemplate.queryForObject(
                        "INSERT INTO \"Especie\" (\"id_Especie\", \"nome_Especie\") VALUES (?, ?) RETURNING \"id_Especie\"",
                        Integer.class,
                        nextEspecieId,
                        dto.getEspecieNome().trim()
                    );
                }
            }

            // 3. Resolução da Raça
            Integer idRaca = null;
            String nomeRaca = (dto.getRacaNome() != null && !dto.getRacaNome().isBlank()) 
                                ? dto.getRacaNome().trim() 
                                : "SRD";

            List<Integer> listR = jdbcTemplate.query(
                "SELECT \"id_Raca\" FROM \"Raca\" WHERE LOWER(\"nome_Raca\") = LOWER(?) AND \"id_Especie\" = ? LIMIT 1",
                (rs, rowNum) -> rs.getInt("id_Raca"),
                nomeRaca, idEspecie
            );

            if (!listR.isEmpty()) {
                idRaca = listR.get(0);
            } else {
                Integer nextRacaId = jdbcTemplate.queryForObject(
                    "SELECT COALESCE(MAX(\"id_Raca\"), 0) + 1 FROM \"Raca\"",
                    Integer.class
                );
                idRaca = jdbcTemplate.queryForObject(
                    "INSERT INTO \"Raca\" (\"id_Raca\", \"nome_Raca\", \"id_Especie\") VALUES (?, ?) RETURNING \"id_Raca\"",
                    Integer.class,
                    nextRacaId,
                    nomeRaca,
                    idEspecie
                );
            }

            // 4. Inserção do Pet
            Integer nextPetId = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(\"id_Pet\"), 0) + 1 FROM \"Pet\"",
                Integer.class
            );

            String sqlPet = """
                INSERT INTO "Pet" (
                    "id_Pet", "nome_Pet", "id_Raca", "idade_Pet", "peso_Pet", "id_Genero", "id_Tutor"
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING "id_Pet"
            """;

            Integer idPet = jdbcTemplate.queryForObject(
                sqlPet,
                Integer.class,
                nextPetId,
                dto.getNomePet().trim(),
                idRaca,
                dto.getIdadePet(),
                dto.getPesoPet(),
                idGenero,
                dto.getIdTutor()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", idPet, "mensagem", "Pet cadastrado com sucesso!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar pet: " + e.getMessage());
        }
    }
}
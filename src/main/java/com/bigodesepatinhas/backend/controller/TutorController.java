package com.bigodesepatinhas.backend.controller;

import com.bigodesepatinhas.backend.dto.TutorCadastroDTO;
import com.bigodesepatinhas.backend.dto.TutorResponseDTO;
import com.bigodesepatinhas.backend.model.Tutor;
import com.bigodesepatinhas.backend.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Tutor>> listarTutores() {
        return ResponseEntity.ok(tutorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        String sql = """
            SELECT 
                t."id_Tutor",
                t."nome_Tutor",
                t."CPF",
                t."telefone_Tutor",
                t."email_Tutor",
                e."numero_Endereco",
                e."CEP",
                r."nome_Rua",
                b."nome_Bairro",
                c."nome_Cidade",
                est."nome_Estado"
            FROM "Tutor" t
            LEFT JOIN "Endereco" e ON t."id_Endereco" = e."id_Endereco"
            LEFT JOIN "Rua" r ON e."id_Rua" = r."id_Rua"
            LEFT JOIN "Bairro" b ON e."id_Bairro" = b."id_Bairro"
            LEFT JOIN "Cidade" c ON b."id_Cidade" = c."id_Cidade"
            LEFT JOIN "Estado" est ON c."id_Estado" = est."id_Estado"
            WHERE t."id_Tutor" = ?
        """;

        List<TutorResponseDTO> tutores = jdbcTemplate.query(sql, (rs, rowNum) -> {
            TutorResponseDTO dto = new TutorResponseDTO();
            dto.setIdTutor(rs.getLong("id_Tutor"));
            dto.setNomeTutor(rs.getString("nome_Tutor"));
            dto.setCpf(rs.getString("CPF"));
            dto.setTelefone(rs.getString("telefone_Tutor"));
            dto.setEmail(rs.getString("email_Tutor"));
            dto.setRua(rs.getString("nome_Rua"));
            dto.setNumero((Integer) rs.getObject("numero_Endereco"));
            dto.setBairro(rs.getString("nome_Bairro"));
            dto.setCidade(rs.getString("nome_Cidade"));
            dto.setEstado(rs.getString("nome_Estado"));
            dto.setCep(rs.getString("CEP"));
            return dto;
        }, id);

        if (tutores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tutores.get(0));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarTutorCompleto(@RequestBody TutorCadastroDTO dto) {
        try {
            // 1. Estado (busca por nome exato ou cria)
            String estadoNome = dto.getEstadoNome() != null ? dto.getEstadoNome().trim() : "";
            Integer idEstado = null;
            if (!estadoNome.isEmpty()) {
                List<Integer> listEstado = jdbcTemplate.query(
                    "SELECT \"id_Estado\" FROM \"Estado\" WHERE LOWER(\"nome_Estado\") = LOWER(?) LIMIT 1",
                    (rs, rowNum) -> rs.getInt("id_Estado"),
                    estadoNome
                );
                idEstado = listEstado.isEmpty() ? null : listEstado.get(0);

                if (idEstado == null) {
                    idEstado = jdbcTemplate.queryForObject(
                        "INSERT INTO \"Estado\" (\"nome_Estado\") VALUES (?) RETURNING \"id_Estado\"",
                        Integer.class,
                        estadoNome
                    );
                }
            }

            // 2. Cidade
            String cidadeNome = dto.getCidadeNome() != null ? dto.getCidadeNome().trim() : "";
            Integer idCidade = null;
            if (!cidadeNome.isEmpty() && idEstado != null) {
                List<Integer> listCidade = jdbcTemplate.query(
                    "SELECT \"id_Cidade\" FROM \"Cidade\" WHERE LOWER(\"nome_Cidade\") = LOWER(?) AND \"id_Estado\" = ? LIMIT 1",
                    (rs, rowNum) -> rs.getInt("id_Cidade"),
                    cidadeNome, idEstado
                );
                idCidade = listCidade.isEmpty() ? null : listCidade.get(0);

                if (idCidade == null) {
                    idCidade = jdbcTemplate.queryForObject(
                        "INSERT INTO \"Cidade\" (\"nome_Cidade\", \"id_Estado\") VALUES (?, ?) RETURNING \"id_Cidade\"",
                        Integer.class,
                        cidadeNome, idEstado
                    );
                }
            }

            // 3. Bairro
            String bairroNome = dto.getBairroNome() != null ? dto.getBairroNome().trim() : "";
            Integer idBairro = null;
            if (!bairroNome.isEmpty() && idCidade != null) {
                List<Integer> listBairro = jdbcTemplate.query(
                    "SELECT \"id_Bairro\" FROM \"Bairro\" WHERE LOWER(\"nome_Bairro\") = LOWER(?) AND \"id_Cidade\" = ? LIMIT 1",
                    (rs, rowNum) -> rs.getInt("id_Bairro"),
                    bairroNome, idCidade
                );
                idBairro = listBairro.isEmpty() ? null : listBairro.get(0);

                if (idBairro == null) {
                    idBairro = jdbcTemplate.queryForObject(
                        "INSERT INTO \"Bairro\" (\"nome_Bairro\", \"id_Cidade\") VALUES (?, ?) RETURNING \"id_Bairro\"",
                        Integer.class,
                        bairroNome, idCidade
                    );
                }
            }

            // 4. Rua
            String ruaNome = dto.getRuaNome() != null ? dto.getRuaNome().trim() : "";
            Integer idRua = null;
            if (!ruaNome.isEmpty()) {
                List<Integer> listRua = jdbcTemplate.query(
                    "SELECT \"id_Rua\" FROM \"Rua\" WHERE LOWER(\"nome_Rua\") = LOWER(?) LIMIT 1",
                    (rs, rowNum) -> rs.getInt("id_Rua"),
                    ruaNome
                );
                idRua = listRua.isEmpty() ? null : listRua.get(0);

                if (idRua == null) {
                    idRua = jdbcTemplate.queryForObject(
                        "INSERT INTO \"Rua\" (\"nome_Rua\") VALUES (?) RETURNING \"id_Rua\"",
                        Integer.class,
                        ruaNome
                    );
                }
            }

            // 5. Endereco
            Integer idEndereco = null;
            if (idBairro != null && idRua != null) {
                idEndereco = jdbcTemplate.queryForObject(
                    "INSERT INTO \"Endereco\" (\"id_Bairro\", \"id_Rua\", \"numero_Endereco\", \"CEP\") VALUES (?, ?, ?, ?) RETURNING \"id_Endereco\"",
                    Integer.class,
                    idBairro, idRua, dto.getNumeroEndereco(), dto.getCep()
                );
            }

            // 6. Tutor vinculado ao id_Endereco
            Tutor tutor = new Tutor();
            tutor.setNomeTutor(dto.getNomeTutor());
            tutor.setCpf(dto.getCpf());
            tutor.setTelefoneTutor(dto.getTelefone());
            tutor.setEmailTutor(dto.getEmail());
            tutor.setIdEndereco(idEndereco);

            Tutor salvo = tutorRepository.save(tutor);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar cadastro: " + e.getMessage());
        }
    }
}
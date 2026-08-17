package com.bigodesepatinhas.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Tutor")
    private Long idTutor;

    @Column(name = "nome_Tutor", nullable = false)
    private String nomeTutor;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    public Tutor() {}

    public Long getIdTutor() { return idTutor; }
    public void setIdTutor(Long idTutor) { this.idTutor = idTutor; }

    public String getNomeTutor() { return nomeTutor; }
    public void setNomeTutor(String nomeTutor) { this.nomeTutor = nomeTutor; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
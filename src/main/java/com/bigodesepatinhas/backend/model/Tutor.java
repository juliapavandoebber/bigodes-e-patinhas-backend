package com.bigodesepatinhas.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "\"Tutor\"")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id_Tutor\"")
    @JsonProperty("idTutor")
    private Long idTutor;

    @Column(name = "\"nome_Tutor\"")
    @JsonProperty("nomeTutor")
    private String nomeTutor;

    @Column(name = "\"CPF\"")
    @JsonProperty("cpf")
    private String cpf;

    @Column(name = "\"telefone_Tutor\"")
    @JsonProperty("telefone")
    private String telefoneTutor;

    @Column(name = "\"email_Tutor\"")
    @JsonProperty("email")
    private String emailTutor;

    @Column(name = "\"id_Endereco\"")
    private Integer idEndereco;

    public Tutor() {}

    public Long getIdTutor() { return idTutor; }
    public void setIdTutor(Long idTutor) { this.idTutor = idTutor; }

    public String getNomeTutor() { return nomeTutor; }
    public void setNomeTutor(String nomeTutor) { this.nomeTutor = nomeTutor; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefoneTutor() { return telefoneTutor; }
    public void setTelefoneTutor(String telefoneTutor) { this.telefoneTutor = telefoneTutor; }

    public String getEmailTutor() { return emailTutor; }
    public void setEmailTutor(String emailTutor) { this.emailTutor = emailTutor; }

    public Integer getIdEndereco() { return idEndereco; }
    public void setIdEndereco(Integer idEndereco) { this.idEndereco = idEndereco; }
}
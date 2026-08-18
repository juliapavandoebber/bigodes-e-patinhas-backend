package com.bigodesepatinhas.backend.dto;

import java.math.BigDecimal;

public class PetCadastroDTO {
    private String nomePet;
    private String especieNome;
    private String racaNome;
    private Integer idadePet;
    private BigDecimal pesoPet;
    private String generoNome;
    private Integer idTutor;

    public PetCadastroDTO() {}

    public String getNomePet() { return nomePet; }
    public void setNomePet(String nomePet) { this.nomePet = nomePet; }
    public String getEspecieNome() { return especieNome; }
    public void setEspecieNome(String especieNome) { this.especieNome = especieNome; }
    public String getRacaNome() { return racaNome; }
    public void setRacaNome(String racaNome) { this.racaNome = racaNome; }
    public Integer getIdadePet() { return idadePet; }
    public void setIdadePet(Integer idadePet) { this.idadePet = idadePet; }
    public BigDecimal getPesoPet() { return pesoPet; }
    public void setPesoPet(BigDecimal pesoPet) { this.pesoPet = pesoPet; }
    public String getGeneroNome() { return generoNome; }
    public void setGeneroNome(String generoNome) { this.generoNome = generoNome; }
    public Integer getIdTutor() { return idTutor; }
    public void setIdTutor(Integer idTutor) { this.idTutor = idTutor; }
}
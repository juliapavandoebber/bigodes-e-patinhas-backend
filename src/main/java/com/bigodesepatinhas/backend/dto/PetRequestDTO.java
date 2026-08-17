package com.bigodesepatinhas.backend.dto;

public class PetRequestDTO {
    private String nomePet;
    private Integer idade;
    private Double peso;
    private Long idRaca;
    private Long idGenero;
    private Long idTutor;

    public PetRequestDTO() {}

    public String getNomePet() { return nomePet; }
    public void setNomePet(String nomePet) { this.nomePet = nomePet; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Long getIdRaca() { return idRaca; }
    public void setIdRaca(Long idRaca) { this.idRaca = idRaca; }

    public Long getIdGenero() { return idGenero; }
    public void setIdGenero(Long idGenero) { this.idGenero = idGenero; }

    public Long getIdTutor() { return idTutor; }
    public void setIdTutor(Long idTutor) { this.idTutor = idTutor; }
}
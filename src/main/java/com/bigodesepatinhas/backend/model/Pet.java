package com.bigodesepatinhas.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Pet")
    private Long idPet;

    @Column(name = "nome_Pet", nullable = false)
    private String nomePet;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "id_Raca")
    private Long idRaca;

    @Column(name = "id_Genero")
    private Long idGenero;

    @ManyToOne
    @JoinColumn(name = "id_Tutor", nullable = false)
    private Tutor tutor;

    public Pet() {}

    public Long getIdPet() { return idPet; }
    public void setIdPet(Long idPet) { this.idPet = idPet; }

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

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }
}
package com.bigodesepatinhas.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AtendimentoVet")
public class AtendimentoVet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_AtendimentoVet")
    private Long idAtendimentoVet;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "data_Atendimento")
    private LocalDate dataAtendimento;

    @ManyToOne
    @JoinColumn(name = "id_Pet", nullable = false)
    private Pet pet;

    public AtendimentoVet() {}

    public Long getIdAtendimentoVet() { return idAtendimentoVet; }
    public void setIdAtendimentoVet(Long idAtendimentoVet) { this.idAtendimentoVet = idAtendimentoVet; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDate getDataAtendimento() { return dataAtendimento; }
    public void setDataAtendimento(LocalDate dataAtendimento) { this.dataAtendimento = dataAtendimento; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
}
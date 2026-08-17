package com.bigodesepatinhas.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Agendamento")
    private Long idAgendamento;

    @Column(name = "data_Agendamento", nullable = false)
    private LocalDate dataAgendamento;

    @Column(name = "horario_Agendamento", nullable = false)
    private LocalTime horarioAgendamento;

    @Column(name = "status_Agendamento")
    private String statusAgendamento = "AGENDADO";

    @ManyToOne
    @JoinColumn(name = "id_Pet", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "id_Veterinario", nullable = false)
    private Veterinario veterinario;

    @Column(name = "id_Servico")
    private Long idServico;

    public Agendamento() {}

    public Long getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Long idAgendamento) { this.idAgendamento = idAgendamento; }

    public LocalDate getDataAgendamento() { return dataAgendamento; }
    public void setDataAgendamento(LocalDate dataAgendamento) { this.dataAgendamento = dataAgendamento; }

    public LocalTime getHorarioAgendamento() { return horarioAgendamento; }
    public void setHorarioAgendamento(LocalTime horarioAgendamento) { this.horarioAgendamento = horarioAgendamento; }

    public String getStatusAgendamento() { return statusAgendamento; }
    public void setStatusAgendamento(String statusAgendamento) { this.statusAgendamento = statusAgendamento; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public Long getIdServico() { return idServico; }
    public void setIdServico(Long idServico) { this.idServico = idServico; }
}
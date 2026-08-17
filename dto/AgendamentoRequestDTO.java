package com.bigodesepatinhas.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoRequestDTO {
    private LocalDate data;
    private LocalTime horario;
    private Long idPet;
    private Long idVeterinario;
    private Long idServico;

    public AgendamentoRequestDTO() {}

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }

    public Long getIdPet() { return idPet; }
    public void setIdPet(Long idPet) { this.idPet = idPet; }

    public Long getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(Long idVeterinario) { this.idVeterinario = idVeterinario; }

    public Long getIdServico() { return idServico; }
    public void setIdServico(Long idServico) { this.idServico = idServico; }
}
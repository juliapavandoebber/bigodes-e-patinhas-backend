package com.bigodesepatinhas.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class DisponibilidadeDTO {
    private Long idVeterinario;
    private LocalDate data;
    private LocalTime horario;
    private Boolean disponivel;

    public DisponibilidadeDTO() {}

    public Long getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(Long idVeterinario) { this.idVeterinario = idVeterinario; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}
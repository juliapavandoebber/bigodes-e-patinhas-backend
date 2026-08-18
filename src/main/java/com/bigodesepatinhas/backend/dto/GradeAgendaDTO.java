package com.bigodesepatinhas.backend.dto;

import java.util.List;

public class GradeAgendaDTO {
    private Object idVeterinario;
    private String data;
    private List<String> horarios;

    public GradeAgendaDTO() {}

    public GradeAgendaDTO(Object idVeterinario, String data, List<String> horarios) {
        this.idVeterinario = idVeterinario;
        this.data = data;
        this.horarios = horarios;
    }

    public Object getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Object idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }
}
package com.bigodesepatinhas.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Veterinario")
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Veterinario")
    private Long idVeterinario;

    @Column(name = "nome_Veterinario", nullable = false)
    private String nomeVeterinario;

    @Column(name = "CRMV", nullable = false)
    private String crmv;

    @Column(name = "telefone_Veterinario")
    private String telefoneVeterinario;

    public Veterinario() {}

    public Long getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(Long idVeterinario) { this.idVeterinario = idVeterinario; }

    public String getNomeVeterinario() { return nomeVeterinario; }
    public void setNomeVeterinario(String nomeVeterinario) { this.nomeVeterinario = nomeVeterinario; }

    public String getCrmv() { return crmv; }
    public void setCrmv(String crmv) { this.crmv = crmv; }

    public String getTelefoneVeterinario() { return telefoneVeterinario; }
    public void setTelefoneVeterinario(String telefoneVeterinario) { this.telefoneVeterinario = telefoneVeterinario; }
}
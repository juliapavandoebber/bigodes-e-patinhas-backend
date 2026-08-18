package com.bigodesepatinhas.backend.dto;

public class TutorResponseDTO {
    private Long idTutor;
    private String nomeTutor;
    private String cpf;
    private String telefone;
    private String email;
    private String rua;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public TutorResponseDTO() {}

    public Long getIdTutor() { return idTutor; }
    public void setIdTutor(Long idTutor) { this.idTutor = idTutor; }
    public String getNomeTutor() { return nomeTutor; }
    public void setNomeTutor(String nomeTutor) { this.nomeTutor = nomeTutor; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}
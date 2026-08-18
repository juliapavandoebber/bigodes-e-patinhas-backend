package com.bigodesepatinhas.backend.dto;

public class TutorCadastroDTO {
    private String nomeTutor;
    private String cpf;
    private String telefone;
    private String email;
    private String estadoNome;
    private String cidadeNome;
    private String bairroNome;
    private String ruaNome;
    private Integer numeroEndereco;
    private String cep;

    public TutorCadastroDTO() {}

    public String getNomeTutor() { return nomeTutor; }
    public void setNomeTutor(String nomeTutor) { this.nomeTutor = nomeTutor; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEstadoNome() { return estadoNome; }
    public void setEstadoNome(String estadoNome) { this.estadoNome = estadoNome; }
    public String getCidadeNome() { return cidadeNome; }
    public void setCidadeNome(String cidadeNome) { this.cidadeNome = cidadeNome; }
    public String getBairroNome() { return bairroNome; }
    public void setBairroNome(String bairroNome) { this.bairroNome = bairroNome; }
    public String getRuaNome() { return ruaNome; }
    public void setRuaNome(String ruaNome) { this.ruaNome = ruaNome; }
    public Integer getNumeroEndereco() { return numeroEndereco; }
    public void setNumeroEndereco(Integer numeroEndereco) { this.numeroEndereco = numeroEndereco; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}
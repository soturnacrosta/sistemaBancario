package com.github.soturnacrosta.model.bean;

public class Usuario {

    private String cpf;
    private String senha;
    private String nome;
    private int idUsuario;

    public Usuario() {
        
    }
    
    public Usuario(String cpf, String senha, String nome, int idUsuario) {
        this.cpf = cpf;
        this.senha = senha;
        this.nome = nome;
        this.idUsuario = idUsuario;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}

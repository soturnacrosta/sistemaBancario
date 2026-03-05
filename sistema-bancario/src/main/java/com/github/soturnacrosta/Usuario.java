package com.github.soturnacrosta;

public class Usuario {

    private ContaBancaria contaBancaria;
    private String senha, nome, cpf;

            public Usuario(ContaBancaria contaBancaria, String senha, String nome, String cpf) {
            this.contaBancaria = contaBancaria;
            this.senha = senha;
            this.nome = nome;
            this.cpf = cpf;
        }

        public ContaBancaria getContaBancaria() {
            return contaBancaria;
        }

        public void setContaBancaria(ContaBancaria contaBancaria) {
            this.contaBancaria = contaBancaria;
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

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
        
}

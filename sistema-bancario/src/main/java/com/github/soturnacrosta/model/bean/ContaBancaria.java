package com.github.soturnacrosta.model.bean;

public class ContaBancaria {

    private int numero;
    private String agencia;
    private double saldo;
    private Usuario usuario_cpf;

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getAgencia() {
        return agencia;
    }
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public Usuario getUsuario_cpf() {
        return usuario_cpf;
    }
    public void setUsuario_cpf(Usuario usuario_cpf) {
        this.usuario_cpf = usuario_cpf;
    }
   
}

package com.github.soturnacrosta.model.bean;

import java.sql.Timestamp;

public class Transacao {

    private int idTransacao;
    private double valor;
    private String descricao;
    private Timestamp data;
    private ContaBancaria contaDestino; // será convertida para texto na DAO
    private ContaBancaria contaOrigem; // chave estrangeira faz ligação por associação
    
    public Transacao() {

    }

    public Transacao(int idTransacao, double valor, String descricao, Timestamp data, ContaBancaria contaDestino,
            ContaBancaria contaOrigem) {
                
        this.idTransacao = idTransacao;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.contaDestino = contaDestino;
        this.contaOrigem = contaOrigem;
        
    }

    public int getIdTransacao() {
        return idTransacao;
    }
    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Timestamp getData() {
        return data;
    }
    public void setData(Timestamp data) {
        this.data = data;
    }
    public ContaBancaria getContaDestino() {
        return contaDestino;
    }
    public void setContaDestino(ContaBancaria contaDestino) {
        this.contaDestino = contaDestino;
    }
    public ContaBancaria getContaOrigem() {
        return contaOrigem;
    }
    public void setContaOrigem(ContaBancaria contaOrigem) {
        this.contaOrigem = contaOrigem;
    }
    
}

package com.github.soturnacrosta;
import java.util.Date;

public class Transacao {

    protected double valor;
    protected Date data;
    protected String descricao;

    public Transacao(double valor, Date data, String descricao) { // precisa do construtor para gerar o recibo
        this.valor = valor;
        this.data = new Date(); // gera a data ATUAL!!
        this.descricao = descricao;
    }

    // getters e setters para que a classe Conta Bancaria consiga ler, respeitando o projeto
    public double getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }
    
}

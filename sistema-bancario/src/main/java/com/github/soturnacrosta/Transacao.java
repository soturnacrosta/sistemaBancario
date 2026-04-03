package com.github.soturnacrosta;
import java.util.Date;

public class Transacao { // responsável por registrar os dados das transações. útil para o extrato

    private double valor;
    private Date data;
    private String descricao;
    private String contaDestino;
    private int idTransacao; // quem faz a contagem é o MYSQL/BANCO DE DADOS!!!

    public Transacao(double valor, String contaDestino, String descricao) { // precisa do construtor para gerar o recibo
        // não precisa passar DATE como parâmetro!!!
        this.valor = valor;
        this.contaDestino = contaDestino;
        this.descricao = descricao;
        this.data = new Date(); // gera a data ATUAL!!

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

    public String getContaDestino() {
        return contaDestino;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

}

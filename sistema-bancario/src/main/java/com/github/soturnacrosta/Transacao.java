package com.github.soturnacrosta;
import java.util.Date;

public class Transacao {

    protected double valor;
    protected Date data;
    protected String descricao;
    private ContaBancaria contaBancaria;
    private Usuario usuarioTransacao;

        public void realizarTed (double valor) { // registro do extrato

            this.valor = valor; // recebe valor do método para registrar no extrato

            System.out.println("Nome: " + usuarioTransacao.getNome());
            System.out.println("Valor: " + valor);
            System.out.println("Data: " + valor);
            System.out.println("Saldo: " + contaBancaria.getSaldo());

        }
    
}

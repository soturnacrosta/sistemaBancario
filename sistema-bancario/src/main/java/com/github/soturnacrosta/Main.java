package com.github.soturnacrosta;

public class Main {
    public static void main(String[] args) {

        Controle painel = new Controle();

        ContaBancaria conta = new ContaBancaria();
        conta.setConta("123");
        conta.setSaldo(300);
        conta.contasAbertas.add(conta);

        painel.painelControle();

    }
}
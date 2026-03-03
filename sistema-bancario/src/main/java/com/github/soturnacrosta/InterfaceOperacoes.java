package com.github.soturnacrosta;

public interface InterfaceOperacoes { // operações bancárias básicas para implementar em Operacoes

    double sacar (double saque, double saldo); 
    
    double depositar (double deposito, double saldo);

    double realizarTed (double ted, double saldo);

}

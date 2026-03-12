package com.github.soturnacrosta;
import java.text.DecimalFormat;

public class SaldoInsuficienteException extends RuntimeException {
    
    public SaldoInsuficienteException(double saldo, double valorTentado) { // formata abaixo e manda pra cá
        // Chamamos o método auxiliar dentro do super
        super(formatarMensagem(saldo, valorTentado));
        
    }
    // Use um método estático auxiliar para formatar a String ANTES do super
    private static String formatarMensagem(double saldo, double valor) {

        DecimalFormat df = new DecimalFormat("#,##0.00");

        return "\nErro! Não há saldo suficiente na conta.\n" +
               "Saldo disponível: R$ " + df.format(saldo) + ".\n" +
               "Valor tentado: R$ " + df.format(valor) + ".\n";

    }

}

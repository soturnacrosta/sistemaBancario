package com.github.soturnacrosta;

public class EntradaInvalidaException extends RuntimeException { // exceptions de valores errados do usuario

    public EntradaInvalidaException () {

        super("\nErro! Entrada inválida. Por favor, digite apenas números e use vírgula para centavos.\nSaindo...\n");

    }

}

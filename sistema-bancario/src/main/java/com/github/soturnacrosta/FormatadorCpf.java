package com.github.soturnacrosta;

public class FormatadorCpf {

    public static String formatarCpf (String cpf) { // método de formatação

        // Verifica se o CPF tem exatamente 11 dígitos antes de tentar formatar
        if (cpf != null && cpf.length() == 11 && cpf.matches ("\\d+")) {
            // \\d+ significa que só podem ser inseridos numeros sem letras e espaços
            return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        }

        return cpf; // Se não tiver 11 dígitos, retorna como está para não quebrar o sistema

    }

    public static boolean isFormatoValido (String cpf) { // barramento de entradas cpf invalidos

        if (cpf == null) {

            return false;

        }

        else { // formatação de entrada inválida

            return cpf.matches("\\d{11}") || cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
        }

    }

}

package com.github.soturnacrosta;

public class ValidarEntrada {

    // Valida NOME: Aceita letras (maíusculas e minúsculas), acentos, espaços, de 1 a 8 caracteres.
    public static boolean isNomeValido (String nome) {

        if (nome == null || nome.trim().isEmpty()) {

            return false;

        }
        // ^ indica o começo, $ indica o fim. 
        // [a-zA-ZÀ-ÿ\\s] permite letras de A a Z, acentos e espaços.
        // {1,8} define o tamanho mínimo (1) e máximo (45).
        return nome.matches("^[a-zA-ZÀ-ÿ\\s]{1,45}$");

    }

    // Valida SENHA: Aceita APENAS letras (sem espaços, sem acentos), de 1 a 8 caracteres.
    public static boolean isSenhaValida (String senha) {

        if (senha == null || senha.trim().isEmpty()) {

            return false;

        }

        // [a-zA-Z] permite apenas letras e numeros.
        return senha.matches("^[a-zA-Z0-9]{1,8}$");

    }

}

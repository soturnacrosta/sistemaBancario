package com.github.soturnacrosta;

public class Main {
    public static void main(String[] args) {

        Gerente gerente = new Gerente(); // o gerente que vai administrar o banco é único
        Usuario usuarioGerente = new Usuario(null, "admin", "admin", "1234"); // esse é o acesso dele
        gerente.usuarios.add(usuarioGerente); //o adiciona a lista de usuarios

        Controle painel = new Controle();
        painel.painelControle();

    }
}
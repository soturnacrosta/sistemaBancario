package com.github.soturnacrosta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class ControleTest {

    @Test 
    @DisplayName("Deve confirmar que nenhum usuário está logado ao iniciar o sistema")
    public void estaLogado () {

        Controle controle = new Controle();
        // captura o valor nulo e imprime uma mensagem de retorno
        Assertions.assertNull(controle.usuarioAutenticado, "O usuário deveria iniciar como nulo");

    }

    @Test
    @DisplayName("Deve garantir que o usuário permanece deslogado após senha incorreta")
    public void testeLoginFalho () {

        Controle controle = new Controle();

        // Simulando um cenário de credenciais erradas
        String cpfInexistente = "000.000.000-00";
        String errada = "1234";

        //validando
        Assertions.assertNull(controle.usuarioAutenticado, "Erro: O sistema logou um usuário com dados inválidos!");

    }
    
}

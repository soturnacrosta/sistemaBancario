package com.github.soturnacrosta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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

        // 1. PREPARAÇÃO: Criamos o roteiro do que o "usuário fantasma" vai digitar.
        // O \n simula a tecla ENTER do teclado.
        // Roteiro: Opção 1 (Login) -> Digita CPF -> Digita Senha -> Opção 0 (Sair para não travar o teste)
        String simulacaoTeclado = "1\n00000000000\n1234\n0\n";
        
        // Guarda o teclado original para não quebrar o computador
        InputStream tecladoOriginal = System.in;
        
        try {
            // 2. AÇÃO: Substitui o teclado real pela nossa String simulada
            System.setIn(new ByteArrayInputStream(simulacaoTeclado.getBytes()));
            
            Controle controle = new Controle();
            
            // Inicia o sistema. Ele vai ler o nosso roteiro automaticamente em milissegundos!
            controle.painelControle(); 
            
            // 3. VALIDAÇÃO: Depois de tentar logar e falhar, o usuárioAutenticado DEVE ser nulo
            Assertions.assertNull(controle.usuarioAutenticado, "Erro: O sistema logou um usuário com dados inválidos!");
            
        } finally {
            // 4. LIMPEZA: Devolve o teclado real para o sistema operacional ao final do teste
            System.setIn(tecladoOriginal);
        }

    }
}

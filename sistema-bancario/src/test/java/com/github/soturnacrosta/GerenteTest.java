package com.github.soturnacrosta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class GerenteTest {

    @Test
    @DisplayName("Testa se abre conta duplicada")
    public void consegueAbrirConta () {

        Gerente gerente = new Gerente();

        //cria uma conta
        Usuario usuario1 = new Usuario(new ContaBancaria(), "senha123", "Mailton", "123.456.789-00");
        gerente.abrirConta(usuario1);

        //cria variavel para contar numero de contas criadas
        int tamanhoAntes = Gerente.usuarios.size();
        
        //cria uma segunda conta com mesmo cpf 
        Usuario usuario2 = new Usuario(new ContaBancaria(), "outrasenha", "Outro Nome", "123.456.789-00");
        gerente.abrirConta(usuario2);

        //testa se o numero de contas aumentaram
        Assertions.assertEquals(tamanhoAntes, Gerente.usuarios.size(), "Erro: O sistema permitiu cadastrar dois usuários com o mesmo CPF!");  

    }

}

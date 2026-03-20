package com.github.soturnacrosta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ContaBancariaTest {

    @Test
    @DisplayName("Testa se aceita valor inválido no depósito")
    public void aceitaDepositoNegativo () {

        ContaBancaria contaBancaria = new ContaBancaria();

        contaBancaria.depositar(-300);

        //não tente comparar double com uma mensagem de erro (String). compare double com double (valor vs saldo bancário)
        //é esperado que quando erre o digito no depósito, o saldo continue sendo zero.
        Assertions.assertEquals(0, contaBancaria.getSaldo(), "O saldo não deveria ter mudado após depósito negativo");

    }

    @Test
    @DisplayName("Testa se aceita valor inválido no saque")
    public void aceitaSaqueNegativo () {

        ContaBancaria contaBancaria = new ContaBancaria();
        
        try {

            contaBancaria.sacar(-300, "123");

        } 
        
        catch (SaldoInsuficienteException e) {

        }

        Assertions.assertEquals(0, contaBancaria.getSaldo(), "O saldo não deveria ter mudado após depósito negativo");
    }

    @Test
    @DisplayName("Testa se aceita valor inválido no TED")
    public void aceitaTedInvalido () {

        ContaBancaria remetente = new ContaBancaria();
        ContaBancaria destino = new ContaBancaria();
        ContaBancaria.contasAbertas.add(destino); // Adiciona na lista para o sistema achar

        // Agora o sistema vai achar a conta e chegar na validação de VALOR
        // Aqui ele deve disparar a Exception e "quebrar" se não tiver try-catch ou assertThrows
        try {
            
            remetente.realizarTed(-300, destino.getNumero(), "Teste");

        }

        catch (SaldoInsuficienteException e) {


        }
        
        Assertions.assertEquals(0, remetente.getSaldo());

    }

    @Test
    @DisplayName("Aceita TED sem destinatario?")
    public void aceitaTedNull () {

        ContaBancaria remetente = new ContaBancaria();
    
        remetente.setSaldo(500); // Dá um saldo inicial para o teste
        remetente.realizarTed(300, null, "teste");

        // O teste passa se o saldo CONTINUAR 500 (ou seja, não descontou nada)
        Assertions.assertEquals(500.0, remetente.getSaldo(),  "Erro: O sistema descontou valor de um TED enviado para destinatário nulo!");

    }

    @Test
    @DisplayName("Aceita TED para o prórpio remetente?")
    public void aceitaTedMesmoDestinatario () {

        ContaBancaria remetente = new ContaBancaria();
        remetente.setSaldo(500); // Dá um saldo inicial
        
        //Passamos o próprio número da conta como destino!
        String proprioNumero = remetente.getNumero();

        remetente.realizarTed(300, proprioNumero, "Transferência para mim mesmo");

        // O teste passa se o saldo CONTINUAR 500 (ou seja, não descontou nada)
        Assertions.assertEquals(500.0, remetente.getSaldo(), "Erro: O sistema descontou valor de um TED enviado para o próprio remetente!");
        
    }

}

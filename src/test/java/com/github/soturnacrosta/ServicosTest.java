package com.github.soturnacrosta;

import org.junit.jupiter.api.Test;

import com.github.soturnacrosta.model.bean.ContaBancaria;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ServicosTest {

    @Test
    @DisplayName("Testa se aceita valor inválido no depósito")
    public void aceitaDepositoNegativo () {

        ContaBancaria contaDestino = new ContaBancaria();
        Servicos contaDestinoDep = new Servicos();

        contaDestinoDep.depositar(contaDestino, -300);

        //não tente comparar double com uma mensagem de erro (String). compare double com double (valor vs saldo bancário)
        //é esperado que quando erre o digito no depósito, o saldo continue sendo zero.
        Assertions.assertEquals(0, contaDestino.getSaldo(), "O saldo não deveria ter mudado após depósito negativo");

    }

    @Test
    @DisplayName("Testa se aceita valor inválido no saque")
    public void aceitaSaqueNegativo () {

        ContaBancaria contaDestino = new ContaBancaria();
        Servicos contaDestinoSac = new Servicos();
        
        try {

            contaDestinoSac.sacar(contaDestino, -300, "123");

        } 
        
        catch (SaldoInsuficienteException e) {

        }

        Assertions.assertEquals(0, contaDestino.getSaldo(), "O saldo não deveria ter mudado após depósito negativo");

    }

    @Test
    @DisplayName("Testa se aceita valor inválido no TED")
    public void aceitaTedInvalido () {

        ArrayList <ContaBancaria> contasAbertas = new ArrayList <> ();

        ContaBancaria remetente = new ContaBancaria();
        ContaBancaria contaDestino = new ContaBancaria();
        Servicos contaOrigem = new Servicos();

        contasAbertas.add(contaDestino); // Adiciona na lista para o sistema achar

        String numeroContaDest = String.valueOf(contaDestino.getNumero());

        // Agora o sistema vai achar a conta e chegar na validação de VALOR
        // Aqui ele deve disparar a Exception e "quebrar" se não tiver try-catch ou assertThrows
        try {
            
            contaOrigem.realizarTed(remetente, -300, numeroContaDest, "Teste");

        }

        catch (SaldoInsuficienteException e) {


        }
        
        Assertions.assertEquals(0, remetente.getSaldo());

    }

    @Test
    @DisplayName("Aceita TED sem destinatario?")
    public void aceitaTedNull () {

        ContaBancaria remetente = new ContaBancaria();
        Servicos contaOrigem = new Servicos();
    
        remetente.setSaldo(500); // Dá um saldo inicial para o teste
        contaOrigem.realizarTed(remetente, 300, null, "teste");

        // O teste passa se o saldo CONTINUAR 500 (ou seja, não descontou nada)
        Assertions.assertEquals(500.0, remetente.getSaldo(),  "Erro: O sistema descontou valor de um TED enviado para destinatário nulo!");

    }

    @Test
    @DisplayName("Aceita TED para o próprio remetente?")
    public void aceitaTedMesmoDestinatario () {

        ContaBancaria remetente = new ContaBancaria();
        Servicos contaOrigem = new Servicos();
        remetente.setSaldo(500); // Dá um saldo inicial
        
        //Passamos o próprio número da conta como destino!
        String proprioNumero = String.valueOf(remetente.getNumero());

        contaOrigem.realizarTed(remetente, 300, proprioNumero, "Transferência para mim mesmo");

        // O teste passa se o saldo CONTINUAR 500 (ou seja, não descontou nada)
        Assertions.assertEquals(500.0, remetente.getSaldo(), "Erro: O sistema descontou valor de um TED enviado para o próprio remetente!");
        
    }

}

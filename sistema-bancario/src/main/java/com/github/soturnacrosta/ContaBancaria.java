package com.github.soturnacrosta;
import java.util.ArrayList;

public class ContaBancaria {
    
    private Operacoes operacoes;
    private String agencia, conta;
    protected double saldo;
    private ArrayList <Transacao> historico = new ArrayList <> (); // para gerar o histórico de transações
    private ArrayList <ContaBancaria> contasAbertas = new ArrayList<>();

        double sacar (double valor) {

            if (valor <= saldo) { // verifica se o saque não excede o saldo da conta

                
                saldo = saldo - valor;

                System.out.println("Saque efetuado com sucesso!");
                System.out.println("Saque: " + valor + "R$.");
                System.out.println("Novo saldo: " + saldo + ".");

                return saldo;

            }

            else {

                System.out.println("Erro! Não há saldo suficiente na conta.");
                System.out.println("Saldo disponível: " + saldo + ".");
               
            }

            return saldo;

        }

        double depositar (double valor) {

            if (valor > 0) { // só pode depositar valores positivos

                saldo = saldo + valor;

                System.out.println("Depósito efetuado com sucesso!");
                System.out.println("Depósito: " + valor + "R$.");
                System.out.println("Novo saldo: " + saldo + ".");

                return saldo;

            }

            else {

                System.out.println("Erro! Não há saldo suficiente na conta.");
                System.out.println("Saldo disponível: " + saldo + ".");

            }
           
            return saldo;

        }

        void realizarTed (double valor, String contaDestino) {

            boolean contaEncontrada = false;

            for (ContaBancaria contasBancaria : contasAbertas) {

                if (contaDestino.equals(contasBancaria.getConta())) {  // conta destino já é o numero da conta!

                    contaEncontrada = true;
                    break;

                }

            }
            
            if (contaEncontrada) {

                if (valor <= saldo) { // verifica se há saldo para transação

                    Transacao novaTransacao = new Transacao(); // tirar extrato - registrando transação
                    // realiza a transação na clase Transacao
                    
                    System.out.println("Transação efetuada com sucesso!");
                    System.out.println("TED no valor de: " + valor + "R$.");
                    System.out.println("Novo saldo: " + saldo + ".");

                }

                else { // caso nao encontrada

                    System.out.println("Erro! Não há saldo suficiente na conta.");
                    System.out.println("Saldo disponível: " + saldo + ".");

                }

            }

            else {

                System.out.println("Erro! Conta " + contaDestino + " não encontrada!");

            }

        }

        public ArrayList<Transacao> getExtrato() { // gera o extrato através de uma arrayList de transações (funções) registradas 

            return historico;

        }

        // getters e setters

        public String getAgencia() {
            return agencia;
        }

        public void setAgencia(String agencia) {
            this.agencia = agencia;
        }

        public String getConta() {
            return conta;
        }

        public void setConta(String conta) {
            this.conta = conta;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        public Operacoes getOperacoes() {
            return operacoes;
        }

        public void setOperacoes(Operacoes operacoes) {
            this.operacoes = operacoes;
        }

        public ArrayList<Transacao> getHistorico() {
            return historico;
        }

        public void setHistorico(ArrayList<Transacao> historico) {
            this.historico = historico;
        }

        public ArrayList<ContaBancaria> getContasAbertas() {
            return contasAbertas;
        }

        public void setContasAbertas(ArrayList<ContaBancaria> contasAbertas) {
            this.contasAbertas = contasAbertas;
        }
        
}

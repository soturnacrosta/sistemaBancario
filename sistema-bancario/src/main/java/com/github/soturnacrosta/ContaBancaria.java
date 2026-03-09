package com.github.soturnacrosta;
import java.util.ArrayList;

public class ContaBancaria {
    
    private Operacoes operacoes;
    private String agencia, conta, numero;
    protected double saldo;
    static int contadorContas = 1000; //contador de numero da conta do cliente
    private ArrayList <Transacao> historico = new ArrayList <> (); // para gerar o histórico de transações
    static ArrayList <ContaBancaria> contasAbertas = new ArrayList<>(); // todas as classes compartilham do mesmo banco da lista
    private Usuario usuario;

        public ContaBancaria() {

            contadorContas++; // Incrementa cada vez que uma nova conta é criada
            this.numero = String.valueOf(contadorContas);

        }
        
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

        void realizarTed (double valor, String contaDestino, String descricao) {

            boolean contaEncontrada = false;

            for (ContaBancaria contasBancaria : contasAbertas) {

                if (contaDestino.equals(contasBancaria.getConta())) {  // conta destino já é o numero da conta!

                    contaEncontrada = true;
                    break;

                }

            }
            
            if (contaEncontrada) {

                if (valor <= saldo) { // verifica se há saldo para transação

                    saldo = saldo - valor;

                    Transacao transacao = new Transacao((valor), contaDestino, descricao); // além de instanciar a lista lá globalmente, instancie o objeto aqui
                    historico.add(transacao); // adicione a lista global
                    
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

        public void imprimirExtrato() { // gera o extrato através de uma arrayList de transações (funções) registradas 

            if (this.historico.isEmpty()) { // verifica se o extrato está vazio

                System.out.println("Histórico vazio.");
                return;

            }

            for (Transacao t : this.historico) { // imprime o extrato pegando os dados na clase Transacao

                Usuario usuario = new Usuario(null, numero, conta, agencia); //instancia usuario para imprimir os valores no extrato
                                
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX EXTRATO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("Destinatário: " + t.getContaDestino());
                System.out.println("Valor: " + t.getValor());
                System.out.println("Data: " + t.getData());
                System.out.println("Descrição: " + t.getDescricao());
                System.out.println();

            }

        }

        // getters e setters

        public String getAgencia() {
            return agencia;
        }

        public void setAgencia(String agencia) {
            this.agencia = agencia;
        }

        public String getConta() {
            return numero;
        }

        public void setConta(String conta) {
            this.numero = numero;
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
        public String getNumero() {
            return numero;
        }

        @Override
        public String toString() {
            return this.numero; // Ou o nome da variável onde você guardou o contador
        }       
}

package com.github.soturnacrosta;
import java.util.ArrayList;

public class ContaBancaria {
    
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

                if (valor > 0) {

                    saldo = saldo - valor;

                    System.out.println();
                    System.out.println("Saque efetuado com sucesso!");
                    System.out.println("Saque: " + valor + "R$.");
                    System.out.println("Novo saldo: " + saldo + ".");
                    System.out.println();

                    return saldo;

                }

                else {

                    System.out.println();
                    System.out.println("Erro! Digite um valor maior que zero!");
                    System.out.println();

                }
               
            }

            else {

                System.out.println();
                System.out.println("Erro! Não há saldo suficiente na conta.");
                System.out.println("Saldo disponível: " + saldo + ".");
                System.out.println();
               
            }

            return saldo;

        }

        double depositar (double valor) {

            if (valor > 0) { // só pode depositar valores positivos

                saldo = saldo + valor;

                System.out.println();
                System.out.println("Depósito efetuado com sucesso!");
                System.out.println("Depósito: " + valor + "R$.");
                System.out.println("Novo saldo: " + saldo + ".");
                System.out.println();

                return saldo;

            }

            else {

                System.out.println();
                System.out.println("Erro! Digite números válidos!");
                System.out.println("Saldo disponível: " + saldo + ".");
                System.out.println();

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
                    
                    System.out.println();
                    System.out.println("Transação efetuada com sucesso!");
                    System.out.println("TED no valor de: " + valor + "R$.");
                    System.out.println("Novo saldo: " + saldo + ".");
                    System.out.println();

                }

                else { // caso nao encontrada

                    System.out.println();
                    System.out.println("Erro! Não há saldo suficiente na conta.");
                    System.out.println("Saldo disponível: " + saldo + ".");
                    System.out.println();

                }

            }

            else {

                System.out.println();
                System.out.println("Erro! Conta " + contaDestino + " não encontrada!");
                System.out.println();

            }

        }

        public void imprimirExtrato() { // gera o extrato através de uma arrayList de transações (funções) registradas 

            if (this.historico.isEmpty()) { // verifica se o extrato está vazio

                System.out.println();
                System.out.println("Histórico vazio.");
                System.out.println();
                return;

            }

            for (Transacao t : this.historico) { // imprime o extrato pegando os dados na clase Transacao
                
                System.out.println();
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

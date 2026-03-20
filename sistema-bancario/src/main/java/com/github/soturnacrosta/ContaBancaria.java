package com.github.soturnacrosta;
import java.util.ArrayList;
import java.util.Scanner;

public class ContaBancaria { // cérebro do sistema. 

    Scanner input = new Scanner (System.in);
    private String agencia, numero;
    protected double saldo;
    static int contadorContas = 1000; //contador de numero da conta do cliente
    private ArrayList <Transacao> historico = new ArrayList <> (); // para gerar o histórico de transações
    static ArrayList <ContaBancaria> contasAbertas = new ArrayList<>(); // todas as classes compartilham do mesmo banco da lista

        public ContaBancaria() {

            contadorContas++; // Incrementa cada vez que uma nova conta é criada
            this.numero = String.valueOf(contadorContas);

        }
        
        double sacar (double valor, String senhaSaque) {

            if (valor > saldo || valor <= 0) { // verifica se o saque não excede o saldo da conta      

                throw new SaldoInsuficienteException(this.saldo, valor);  

            }

            else { // sem saldo

                saldo = saldo - valor;

                System.out.println();
                System.out.println("Saque efetuado com sucesso!");
                System.out.println("Saque: " + MoedaUtilizada.formatar(valor) + ".");
                System.out.println("Novo saldo: " + MoedaUtilizada.formatar(saldo) + ".");
                System.out.println();

                return saldo; 
               
            }

        }

        double depositar (double valor) {

            if (valor > 0) { // só pode depositar valores positivos

                saldo = saldo + valor;

                System.out.println();
                System.out.println("Depósito efetuado com sucesso!");
                System.out.println("Depósito: " + MoedaUtilizada.formatar(valor) + "R$.");
                System.out.println("Novo saldo: " + MoedaUtilizada.formatar(saldo) + ".");
                System.out.println();

                return saldo;

            }

            else { //digito errado

                System.out.println();
                System.out.println("Erro! Digite apenas números válidos (maiores que 0).");
                System.out.println("Saldo disponível: " + saldo + ".");
                System.out.println();

            }
           
            return saldo;

        }

        void realizarTed (double valor, String contaDestino, String descricao) {

            if (contarTedDiario() >= 5) { // verifica limite diário de transações

                System.out.println();
                System.out.println("Erro: Limite de 5 transações TED diárias atingido."); 
                System.out.println();

                return; // Encerra o método sem realizar a transferência

            }

            if (this.numero.equals(contaDestino)) { // compara os numeros das contas

                System.out.println();
                System.out.println("Erro! A conta de destino não deve ser a conta remetente."); // se for a mesma conta
                System.out.println();

                return;

            }

            ContaBancaria contaEncontrada = ContaBancaria.buscarContaPorNumero(contaDestino); //método de buscar contas

            if (contaEncontrada != null) {

                if (valor > saldo || valor <= 0) { // verifica se há saldo para transação

                    throw new SaldoInsuficienteException(this.saldo, valor);

                }

                else {                       

                    saldo = saldo - valor;  //debita o valor na propria conta

                    contaEncontrada.saldo += valor; // acrescenta o valor na conta destino 

                    Transacao transacao = new Transacao((valor), contaDestino, descricao); // além de instanciar a lista lá globalmente, instancie o objeto aqui
                    historico.add(transacao); // adicione a lista global
                    
                    System.out.println();
                    System.out.println("Transação efetuada com sucesso!");
                    System.out.println("TED no valor de: " + MoedaUtilizada.formatar(valor) + ".");
                    System.out.println("Novo saldo: " + MoedaUtilizada.formatar(saldo) + ".");
                    System.out.println();

                }

            }

            else { // caso nao encontrada

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

            int contador = 0;

            for (int i = historico.size() -1; i>= 0 && contador < 10; i --){ // define o teto como 10
                
                Transacao t = historico.get(i); // imprime o extrato pegando os dados na clase Transacao

                System.out.println();
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX EXTRATO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("Destinatário: " + t.getContaDestino());
                System.out.println("Valor: " + MoedaUtilizada.formatar(t.getValor()));
                System.out.println("Data: " + t.getData());
                System.out.println("Descrição: " + t.getDescricao());
                System.out.println();

                contador ++; // incrementa para atingir as 10 impressões e parar

            }

        }

        private int contarTedDiario() { // função para verificar limite de transações diarias

            int contador = 0;
            java.util.Date hoje = new java.util.Date();
            
            java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("dd/MM/yyyy");  // Formatador para comparar apenas Dia/Mês/Ano, ignorando as horas


            String dataAtual = fmt.format(hoje); // pega a data formatada e guarda numa String

            for (Transacao t : historico) {

                // Comparamos a data da transação salva com a data de agora
                 if (fmt.format(t.getData()).equals(dataAtual)) {

                        contador++; // Conta todas do dia, independente da descrição

                    }

                }
            
            return contador;

        }

        public static ContaBancaria buscarContaPorNumero(String numeroProcurado) { // conversor String > Conta bancária

            for (ContaBancaria cb : contasAbertas) {

                if (cb.getNumero().equals(numeroProcurado)) {

                    return cb; // Aqui a String "virou" o Objeto da Classe

                }

            }

            return null; // Caso não encontre nenhuma conta com esse número

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
            this.numero = conta;
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
            ContaBancaria.contasAbertas = contasAbertas;        
            
        }
        public String getNumero() {
            return numero;
        }

        @Override
        public String toString() {
            return this.numero; // Ou o nome da variável onde você guardou o contador
        }       
}

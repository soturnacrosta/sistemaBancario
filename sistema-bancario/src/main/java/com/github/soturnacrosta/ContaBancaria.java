package com.github.soturnacrosta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.github.soturnacrosta.model.dao.ContaBancariaDAO;
import com.github.soturnacrosta.model.dao.TransacaoDAO;
import com.github.soturnacrosta.connection.ConnectionFactory;
import com.github.soturnacrosta.model.bean.Transacao;

public class ContaBancaria { // cérebro do sistema. 

    Scanner input = new Scanner (System.in);
    private String agencia;
    private int numero;
    protected double saldo;
    static int contadorContas = 1000; //contador de numero da conta do cliente
    TransacaoDAO transacaoDao = new TransacaoDAO();
    ContaBancariaDAO contaDao = new ContaBancariaDAO();
    //private ArrayList <Transacao> historico = new ArrayList <> (); // para gerar o histórico de transações
    //static ArrayList <ContaBancaria> contasAbertas = new ArrayList<>(); // todas as classes compartilham do mesmo banco da lista
        
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


            if (contarTedDiario(this.numero) >= 5) { // verifica limite diário de transações

                System.out.println();
                System.out.println("Erro: Limite de 5 transações TED diárias atingido."); 
                System.out.println();

                return; // Encerra o método sem realizar a transferência

            }

            int destinoInt = Integer.parseInt(contaDestino); //converte int com int!

            if (this.numero == destinoInt) { // compara os numeros das contas 

                System.out.println();
                System.out.println("Erro! A conta de destino não deve ser a conta remetente."); // se for a mesma conta
                System.out.println();

                return;

            }

            com.github.soturnacrosta.model.bean.ContaBancaria contaEncontrada = ContaBancaria.buscarContaPorNumero(contaDestino); //método de buscar contas

            if (contaEncontrada != null) {

                if (valor > saldo || valor <= 0) { // verifica se há saldo para transação

                    throw new SaldoInsuficienteException(this.saldo, valor);

                }

                else {                       

                    saldo = saldo - valor;  //debita o valor na propria conta

                    contaEncontrada.setSaldo(getSaldo()+valor);// acrescenta o valor na conta destino 

                    Transacao transacao = new Transacao(); // além de instanciar a lista lá globalmente, instancie o objeto aqui
                    transacao.setValor(valor);

                    int numeroConta = Integer.parseInt(contaDestino); // faz a conversão para int

                    com.github.soturnacrosta.model.bean.ContaBancaria contaDestinoObj = contaDao.readByNumero(numeroConta); //busca o número de conta na DAO
                    
                    transacao.setContaDestino(contaDestinoObj);
                    transacao.setDescricao(descricao);
                    // adicione a lista global
                    
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

        public void imprimirExtrato(int numeroConta) { // gera o extrato através de uma arrayList de transações (funções) registradas 

            TransacaoDAO transacaoDao = new TransacaoDAO();
            // O DAO já deve trazer a lista filtrada e ordenada (SELECT ... LIMIT 10)
            List<com.github.soturnacrosta.model.bean.Transacao> extrato = transacaoDao.readByConta(numeroConta);

            if (extrato.isEmpty()) {

                System.out.println("\nHistórico vazio.\n");

                return;

            }

            int contador = 0;

            for (int i = extrato.size() -1; i>= 0 && contador < 10; i --){ // define o teto como 10
                
                Transacao t = extrato.get(i); // imprime o extrato pegando os dados na clase Transacao

                System.out.println();
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX EXTRATO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("ID Transcao: " + t.getIdTransacao());
                System.out.println("Destinatário: " + t.getContaDestino());
                System.out.println("Valor: " + MoedaUtilizada.formatar(t.getValor()));
                System.out.println("Data: " + t.getData());
                System.out.println("Descrição: " + t.getDescricao());
                System.out.println();

                contador ++; // incrementa para atingir as 10 impressões e parar

            }

        }

        private int contarTedDiario(int numeroConta) { // função para verificar limite de transações diarias

           Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int total = 0;

            try {
                // O SQL conta quantas transações existem para essa conta na data de hoje
                String sql = "SELECT COUNT(*) FROM Transacao WHERE fk_conta_origem = ? AND DATE(data) = CURDATE()";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, numeroConta);
                rs = stmt.executeQuery();

                if (rs.next()) {

                    total = rs.getInt(1);

                }

            } 
            
            catch (SQLException e) {

                throw new RuntimeException("Erro ao contar transações diárias", e);

            } 
            
            finally {

                ConnectionFactory.closeConnection(connection, stmt, rs);

            }

            return total;

        }

        public static com.github.soturnacrosta.model.bean.ContaBancaria buscarContaPorNumero(String numeroProcurado) { // conversor String > Conta bancária

            try {

                ContaBancariaDAO daoTemp = new ContaBancariaDAO();

                int contaProcurada = Integer.parseInt(numeroProcurado);

                com.github.soturnacrosta.model.bean.ContaBancaria contas = daoTemp.readByNumero(contaProcurada);

                return daoTemp.readByNumero(contaProcurada);

            }

            catch (Exception e) {

                return null; // Se der erro na conversão ou no banco

            }

        }

        // getters e setters
        public String getAgencia() {
            return agencia;
        }

        public void setAgencia(String agencia) {
            this.agencia = agencia;
        }

        public int getConta() {
            return numero;
        }

        public void setConta(int conta) {
            this.numero = conta;
        }

        public double getSaldo() {
            return saldo;
        }

        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

}

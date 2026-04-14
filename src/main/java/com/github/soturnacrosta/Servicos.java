package com.github.soturnacrosta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.github.soturnacrosta.connection.ConnectionFactory;
import com.github.soturnacrosta.model.dao.ContaBancariaDAO;
import com.github.soturnacrosta.model.dao.TransacaoDAO;
import com.github.soturnacrosta.model.bean.ContaBancaria;
import com.github.soturnacrosta.model.bean.Transacao;

public class Servicos {

    TransacaoDAO transacaoDao = new TransacaoDAO();
    ContaBancariaDAO contaDao = new ContaBancariaDAO();
    //private ArrayList <Transacao> historico = new ArrayList <> (); // para gerar o histórico de transações
    //static ArrayList <ContaBancaria> contasAbertas = new ArrayList<>(); // todas as classes compartilham do mesmo banco da lista
        
        double sacar (ContaBancaria contaOrigem, double valor, String senhaSaque) {

            if (valor > contaOrigem.getSaldo() || valor <= 0) { // verifica se o saque não excede o saldo da conta      

                throw new SaldoInsuficienteException(contaOrigem.getSaldo(), valor);  

            }

            else { // sem saldo

                contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);

                contaDao.update(contaOrigem);

                System.out.println();
                System.out.println("Saque efetuado com sucesso!");
                System.out.println("Saque: " + MoedaUtilizada.formatar(valor) + ".");
                System.out.println("Novo saldo: " + MoedaUtilizada.formatar(contaOrigem.getSaldo()) + ".");
                System.out.println();

                return contaOrigem.getSaldo(); 
               
            }

        }

        double depositar (ContaBancaria contaOrigem, double valor) {

            if (valor > 0) { // só pode depositar valores positivos

                contaOrigem.setSaldo(contaOrigem.getSaldo() + valor);

                // salva no banco de dados
                contaDao.update(contaOrigem);

                System.out.println();
                System.out.println("Depósito efetuado com sucesso!");
                System.out.println("Depósito: " + MoedaUtilizada.formatar(valor) + "R$.");
                System.out.println("Novo saldo: " + MoedaUtilizada.formatar(contaOrigem.getSaldo()) + ".");
                System.out.println();

                return contaOrigem.getSaldo();

            }

            else { //digito errado

                System.out.println();
                System.out.println("Erro! Digite apenas números válidos (maiores que 0).");
                System.out.println("Saldo disponível: " + contaOrigem.getSaldo() + ".");
                System.out.println();

            }
           
            return contaOrigem.getSaldo();

        }

        void realizarTed (ContaBancaria contaOrigem, double valor, String contaDestino, String descricao) {


            if (contarTedDiario(contaOrigem.getNumero()) >= 5) { // verifica limite diário de transações

                System.out.println();
                System.out.println("Erro: Limite de 5 transações TED diárias atingido."); 
                System.out.println();

                return; // Encerra o método sem realizar a transferência

            }

            int destinoInt;

            try { // conversão para int impedindo nulos

                destinoInt = Integer.parseInt(contaDestino);

            } 
            
            catch (NumberFormatException e) {

                System.out.println("\nErro! O formato da conta destino é inválido.\n");

                return;

            }
            
            if (contaOrigem.getNumero() == destinoInt) { // compara os numeros das contas 

                System.out.println();
                System.out.println("Erro! A conta de destino não deve ser a conta remetente."); // se for a mesma conta
                System.out.println();

                return;

            }

            ContaBancaria contaEncontrada = buscarContaPorNumero(contaDestino); //método de buscar contas

            if (contaEncontrada != null && "ENCERRADA".equals(contaEncontrada.getStatus())) { //utilize o objeto ja criado pra buscar, nada de uma string

                System.out.println();
                System.out.println("Erro! A conta de destino encontra-se encerrada."); // se for a mesma conta
                System.out.println();

                return;

            }

            if (contaEncontrada != null) {

                if (valor > contaOrigem.getSaldo() || valor <= 0) { // verifica se há saldo para transação

                    throw new SaldoInsuficienteException(contaOrigem.getSaldo(), valor);

                }

                else {                       

                    contaOrigem.setSaldo(contaOrigem.getSaldo() - valor); //debita o valor na propria conta

                    contaEncontrada.setSaldo(contaEncontrada.getSaldo()+valor);// acrescenta o valor na conta destino 

                    Transacao transacao = new Transacao(); // além de instanciar a lista lá globalmente, instancie o objeto aqui
                    transacao.setValor(valor);
                    
                    transacao.setContaDestino(contaEncontrada);
                    transacao.setContaOrigem(contaOrigem);
                    transacao.setDescricao(descricao);
                    transacao.setData(new java.sql.Timestamp(System.currentTimeMillis()));
                    
                    transacaoDao.create(transacao);

                    contaDao.update(contaOrigem); // Tira da origem no BD
                    contaDao.update(contaEncontrada); // Bota no destino no BD
                    // adicione a lista global
                    
                    System.out.println();
                    System.out.println("Transação efetuada com sucesso!");
                    System.out.println("TED no valor de: " + MoedaUtilizada.formatar(valor) + ".");
                    System.out.println("Novo saldo: " + MoedaUtilizada.formatar(contaOrigem.getSaldo()) + ".");
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
            List<Transacao> extrato = transacaoDao.readByConta(numeroConta);

            if (extrato.isEmpty()) {

                System.out.println("\nHistórico vazio.\n");

                return;

            }

            int contador = 0;

            for (int i = 0; i < extrato.size() && contador < 10; i++){ // define o teto como 10
                
                Transacao t = extrato.get(i); // imprime o extrato pegando os dados na clase Transacao

                System.out.println();
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX EXTRATO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("ID Transcao: " + t.getIdTransacao());
                System.out.println("Remetente: " + t.getContaOrigem().getNumero()); //busca la na dao, tem que montar a informação
                System.out.println("Destinatário: " + t.getContaDestino().getNumero()); //busca la na dao, tem que montar a informação
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

        public static ContaBancaria buscarContaPorNumero(String numeroProcurado) { // conversor String > Conta bancária

            try {

                ContaBancariaDAO daoTemp = new ContaBancariaDAO();

                int contaProcurada = Integer.parseInt(numeroProcurado);

                ContaBancaria contas = daoTemp.readByNumero(contaProcurada);

                return contas;
                
            }

            catch (Exception e) {

                return null; // Se der erro na conversão ou no banco

            }

        }

}

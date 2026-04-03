package com.github.soturnacrosta.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.soturnacrosta.connection.ConnectionFactory;
import com.github.soturnacrosta.model.bean.ContaBancaria;
import com.github.soturnacrosta.model.bean.Transacao;

public class TransacaoDAO {

    // fazendo o crud

    public void create (Transacao transacao) { //importe a classe correta, do bean!

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {

            stmt = connection.prepareStatement("INSERT INTO Transacao (valor, descricao, data, contaDestino, contaOrigem) VALUES (?, ?, ?, ?, ?, ?)");

            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.getDescricao());
            stmt.setTimestamp(4, transacao.getData());
            stmt.setString(5, String.valueOf(transacao.getContaDestino().getNumero())); // acessa APENAS o numero da conta e converte para STRING
            stmt.setInt(6, transacao.getContaOrigem().getNumero()); // acessa APENAS o numero da conta
            // na tabela a contaOrigem é int

            stmt.executeUpdate();
            System.out.println("Transacão salva com sucesso no banco de dados!");

        }

        catch (SQLException e) {

            Logger.getLogger(TransacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao registrar a transação no histórico do banco de dados.", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt);

        }

    }

    public List<Transacao> read () {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Transacao> transacoes = new ArrayList<>();

        try {

            stmt = connection.prepareStatement("SELECT * FROM Transacao");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Transacao transacao = new Transacao();

                transacao.setIdTransacao(rs.getInt("idTransacao"));
                transacao.setValor(rs.getDouble("valor"));
                transacao.setDescricao(rs.getNString("descricao"));
                transacao.setData(rs.getTimestamp("data"));

                String contaDestino = rs.getString ("contaDestino");
                int contaOrigem = rs.getInt("fk_conta_origem");

                ContaBancaria contaDes = new ContaBancaria();
                ContaBancaria contaOri = new ContaBancaria(); 

                contaOri.setNumero(contaOrigem);

                // convertendo para int
                contaDes.setNumero(Integer.parseInt(contaDestino));

                transacao.setContaOrigem(contaOri);
                transacao.setContaDestino(contaDes);

                transacoes.add(transacao);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(TransacaoDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao buscar as transações no banco de dados", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt, rs); // fecha todos os parâmetros!

        }

        return transacoes;
         
    }

    public List<Transacao> readByConta(int numeroConta) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // Criamos a lista que vai substituir o seu "this.historico"
        ArrayList <Transacao> historicoNoBanco = new ArrayList<>();

        try {
            // Buscamos transações onde a conta foi a origem OU o destino
            String sql = "SELECT * FROM Transacao WHERE fk_conta_origem = ? OR contaDestino = ? ORDER BY data DESC";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
            stmt.setInt(2, numeroConta);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                com.github.soturnacrosta.model.bean.Transacao t = new com.github.soturnacrosta.model.bean.Transacao();
                t.setValor(rs.getDouble("valor"));
                t.setDescricao(rs.getString("descricao"));
                t.setData(rs.getTimestamp("data")); // Se você tiver data no banco

                // Aqui você teria que buscar os objetos ContaBancaria se quiser o Bean completo
                // Mas para um extrato simples, apenas os dados acima já bastam
                
                historicoNoBanco.add(t);
            }

        }
        
        catch (SQLException e) {

            Logger.getLogger(TransacaoDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao ler histórico de transações", e);

        } 
        
        finally {

            ConnectionFactory.closeConnection(connection, stmt, rs);

        }

        return historicoNoBanco;

    }
    
}

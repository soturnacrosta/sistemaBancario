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
import com.github.soturnacrosta.model.bean.Transacao;

public class TransacaoDAO {

    // fazendo o crud

    public void create (Transacao transacao) { //importe a classe correta, do bean!

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {

            stmt = connection.prepareStatement("INSERT INTO Transacao (valor, descricao, data, contaDestino, fk_conta_origem) VALUES (?, ?, ?, ?, ?)");

            stmt.setDouble(1, transacao.getValor());
            stmt.setString(2, transacao.getDescricao());
            stmt.setTimestamp(3, transacao.getData());
            stmt.setString(4, String.valueOf(transacao.getContaDestino().getNumero())); // acessa APENAS o numero da conta e converte para STRING
            stmt.setInt(5, transacao.getContaOrigem().getNumero()); // acessa APENAS o numero da conta
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

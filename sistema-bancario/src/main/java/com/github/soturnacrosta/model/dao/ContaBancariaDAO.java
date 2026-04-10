package com.github.soturnacrosta.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.github.soturnacrosta.model.bean.ContaBancaria; // importe a classe correta, no caso do bean
import com.github.soturnacrosta.model.bean.Usuario;
import com.github.soturnacrosta.connection.ConnectionFactory;

public class ContaBancariaDAO {

    //fazendo o CRUD

    public void create (ContaBancaria conta) {

        Connection connection = ConnectionFactory.getConnection(); // pega a conexão
        PreparedStatement stmt = null;

        try {

            // insere os valores através do statement
            stmt = connection.prepareStatement("INSERT INTO ContaBancaria (agencia, saldo, fk_usuario_cpf) VALUES (?, ?, ?)");
            // quando o atributo for auto-increment, não deve ser enviado via insert. o proprio MYSQL fará o serviço
            stmt.setString(1, conta.getAgencia());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setString(3, conta.getUsuario_cpf().getCpf());

            stmt.executeUpdate(); // retorna os valores para a tabela

            System.out.println();
            System.out.println("Conta bancária salva com sucesso no banco de dados!");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(ContaBancariaDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao registrar a nova conta bancária no banco de dados.", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt);

        }

    }

    public void update (ContaBancaria conta) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            //não permitir atualizar o número da conta, então não coloca o número de conta como variável. apenas identifica no Stmt
            stmt = connection.prepareStatement("UPDATE ContaBancaria SET agencia = ?, saldo = ?, fk_usuario_cpf = ?, status =? WHERE numero = ?");
            stmt.setString(1, conta.getAgencia());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setString(3, conta.getUsuario_cpf().getCpf());
            stmt.setString(4, conta.getStatus());
            stmt.setInt(5, conta.getNumero());

            stmt.executeUpdate();

            System.out.println();
            System.out.println("Conta atualizada com sucesso!");

        } 
        
        catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(ContaBancariaDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao atualizar as contas no banco de dados", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

        }

    }

    public void delete (ContaBancaria conta) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = connection.prepareStatement("UPDATE ContaBancaria SET status = 'ENCERRADA' WHERE numero = ?");
            stmt.setInt(1, conta.getNumero());

            stmt.executeUpdate();

            System.out.println();
            System.out.println("Conta excluída com sucesso!");

        } 
        
        catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(ContaBancariaDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao deletar as contas no banco de dados", e); 

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

        }

    }

    public ContaBancaria readByNumero(int contaBusca) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ContaBancaria contaEncontrada = null;

            try {

                stmt = connection.prepareStatement("SELECT * FROM ContaBancaria WHERE numero = ?");
                stmt.setInt(1, contaBusca);
                rs = stmt.executeQuery();

                if (rs.next()) { // Se achou, monta o objeto

                    contaEncontrada = new ContaBancaria();
                    contaEncontrada.setNumero(rs.getInt("numero"));
                    contaEncontrada.setAgencia(rs.getString("agencia"));
                    contaEncontrada.setSaldo(rs.getDouble("saldo"));
                    contaEncontrada.setStatus(rs.getString("status"));
                    
                    Usuario donoDaConta = new Usuario();

                    // 2. Pega a String do CPF que veio do banco e guarda DENTRO desse Usuario
                    donoDaConta.setCpf(rs.getString("fk_usuario_cpf"));

                    // 3. Agora sim, você pega o objeto Usuario completo e guarda na ContaBancaria
                    contaEncontrada.setUsuario_cpf(donoDaConta);

                }

            } 
            
            catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

                throw new RuntimeException("Erro ao encontrar usuário por CPF no banco de dados", e);  

            }

            finally {

                ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

            }

            return contaEncontrada; // Retorna o usuário pronto ou null se não achar
        
    }

      public ContaBancaria readByCpf(String cpfBusca) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ContaBancaria contaEncontrada = null;

            try {

                stmt = connection.prepareStatement("SELECT * FROM ContaBancaria WHERE fk_usuario_cpf = ?");
                stmt.setString(1, cpfBusca);
                rs = stmt.executeQuery();

                if (rs.next()) { // Se achou, monta o objeto

                    contaEncontrada = new ContaBancaria();
                    contaEncontrada.setNumero(rs.getInt("numero"));
                    contaEncontrada.setAgencia(rs.getString("agencia"));
                    contaEncontrada.setSaldo(rs.getDouble("saldo"));
                    contaEncontrada.setStatus(rs.getString("status"));
                    // ... (seta o resto dos dados)

                    //Instanciar e setar o Usuario dono da conta!
                    Usuario donoDaConta = new Usuario();
                    donoDaConta.setCpf(rs.getString("fk_usuario_cpf")); 
                    contaEncontrada.setUsuario_cpf(donoDaConta);

                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

                throw new RuntimeException("Erro ao encontrar usuário por CPF no banco de dados", e);  

            }

            finally {

                ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

            }

            return contaEncontrada; // Retorna o usuário pronto ou null se não achar
        
    }

}
 
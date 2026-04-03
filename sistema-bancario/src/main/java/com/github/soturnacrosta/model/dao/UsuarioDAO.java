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
import com.github.soturnacrosta.model.bean.Usuario;

public class UsuarioDAO {

    public void create (Usuario usuario) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {

            stmt = connection.prepareStatement("INSERT INTO Usuario (cpf, senha, nome) VALUES (?, ?, ?)");

            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            
            stmt.executeUpdate();
            System.out.println("Usuário salvo com sucesso no banco de dados!");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao cadastrar o novo usuário no banco de dados.", e);  

        }

         finally {

            ConnectionFactory.closeConnection(connection, stmt);

        }

    }

    public List<Usuario> read () {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> usuarios = new ArrayList<>();

        try {

            stmt = connection.prepareStatement("SELECT * FROM Usuario");
            rs = stmt.executeQuery(); 

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setCpf(rs.getString("cpf"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNome(rs.getString("nome"));
                usuario.setIdUsuario(rs.getInt("idUsuario")); //precisa colocar idUsuario para o sistema identificar
                //qual usuario ler 

                usuarios.add(usuario);

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao buscar os usuários no banco de dados", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt, rs); // fecha todos os parâmetros!

        }

        return usuarios;
        
    }
    
    public void update (Usuario usuario) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = connection.prepareStatement("UPDATE Usuario SET cpf = ?, senha = ?, nome = ? WHERE idUsuario = ?");
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setInt(4, usuario.getIdUsuario());

            stmt.executeUpdate();

            System.out.println("Usuário atualizado com sucesso!");

        } 
        
        catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao atualizar os usuários no banco de dados", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

        }

    }

    public void delete (Usuario usuario) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = connection.prepareStatement("DELETE FROM Usuario WHERE idUsuario = ?");
            stmt.setInt(1, usuario.getIdUsuario());

            stmt.executeUpdate();

            System.out.println("Usuário excluído com sucesso!");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro ao deletar usuários no banco de dados", e);  

        }

        finally {

            ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

        }

    }

    public Usuario readByCpf(String cpfBusca) {

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Usuario usuarioEncontrado = null;

            try {

                stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE cpf = ?");
                stmt.setString(1, cpfBusca);
                rs = stmt.executeQuery();

                if (rs.next()) { // Se achou, monta o objeto

                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setCpf(rs.getString("cpf"));
                    // ... (seta o resto dos dados)
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

                throw new RuntimeException("Erro ao encontrar usuário por CPF no banco de dados", e);  

            }

            finally {

                ConnectionFactory.closeConnection(connection, stmt); // fecha todos os parâmetros!

            }

            return usuarioEncontrado; // Retorna o usuário pronto ou null se não achar
        
    }

}

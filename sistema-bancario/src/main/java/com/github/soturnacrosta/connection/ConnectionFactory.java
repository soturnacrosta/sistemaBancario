package com.github.soturnacrosta.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ConnectionFactory {
    
    // instanciando conexão com o banco de dados via localhost
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_bancario";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection () { // tentando conexão

        try { // tenta conectar com o banco de dados
 
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL, USER, PASS);

        }

        catch (ClassNotFoundException e) { // caso não dê certo

            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro na conexão com o banco de dados", e);

        }

        catch (SQLException e) { // caso não dê certo

            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro na conexão com o banco de dados", e);

        }
        
    }

    public static void closeConnection (Connection connection) { //fecha as conexões

        try { 

            if (connection != null) { // caso exista conexão

                connection.close(); // fecha conexão

            }
            
        }

        catch (SQLException e) {

            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro na conexão com o banco de dados", e);

        }

    }

    public static void closeConnection (Connection connection, PreparedStatement stmt) {

       closeConnection(connection);
       
        try { 

            if (stmt != null) {

                stmt.close();

            }
            
        }

        catch (SQLException e) {

            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro na conexão com o banco de dados", e);

        }

    }

    public static void closeConnection (Connection connection, PreparedStatement stmt, ResultSet rs) {

       closeConnection(connection, stmt);
       
        try { 

            if (rs != null) {

                rs.close();

            }
            
        }

        catch (SQLException e) {

            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, e);

            throw new RuntimeException("Erro na conexão com o banco de dados", e);

        }

    }
    
}

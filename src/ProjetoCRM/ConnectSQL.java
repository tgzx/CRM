package ProjetoCRM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ProjetoCRM.MainBanco.enderecoIP;

public class ConnectSQL {
    public static Connection conexao = null;
    public static Connection Conexao() {
        if (conexao != null) {
            return conexao;
        }
        if (enderecoIP.equals("remotemysql.com")){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection("jdbc:mysql://"+ enderecoIP +":3306/WsqcXz6l4e" +
                        "?autoReconnect=true&useSSL=false", "WsqcXz6l4e", "zbUBV2UeNU");
                System.out.println("\nMySQL conectado!\n");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                System.out.println("Conexão com MySQL falhou.");
            }
        } else {
            try {
                conexao = DriverManager.getConnection("jdbc:postgresql://"+ enderecoIP +":5433/CRM_MeuBanco"
                        , "postgres", "Batata2020");
                System.out.println("\nPostgreSQL conectado!\n");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("Conexão com PostgreSQL falhou.");
            }
        }
        return conexao;
    }
}
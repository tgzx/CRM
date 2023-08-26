package ProjetoCRM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ProjetoCRM.MainBanco.enderecoIP;

public class ConnectSQL {
    public static Connection conexao = null;
    public static Connection Conexao() {

        String enderecoIP = MainBanco.enderecoIP;
        String mySQLserver = "sql10.freemysqlhosting.net";
        String mySQLdatabaseName = "sql10642424";
        String mySQLusername = "sql10642424";
        String mySQLpassword = "B6yP26qaNI";
        int port = 3306;

        if (conexao != null) {
            return conexao;
        }
        if (enderecoIP.equals("remotemysql.com")){
            try {
                Class.forName("com.mysql.jdbc.Driver");

                conexao = DriverManager.getConnection(
                    "jdbc:mysql://" + mySQLserver + ":" + port + "/" + mySQLdatabaseName + "?autoReconnect=true&useSSL=false", mySQLusername, mySQLpassword
                );

                //System.out.println("CONEXAO => " + conexao);

                // conexao = DriverManager.getConnection(
                //     "jdbc:mysql://"+ enderecoIP +":3306/WsqcXz6l4e?autoReconnect=true&useSSL=false", "WsqcXz6l4e", "zbUBV2UeNU"
                // );

                System.out.println("\nMySQL conectado!\n");
            } catch (Exception e) {
                System.out.println("Conexão com MySQL falhou. Erro: " + e.getMessage() + "\n");
            }
        } else {
            try {
                conexao = DriverManager.getConnection("jdbc:postgresql://"+ enderecoIP +":5433/CRM_MeuBanco"
                        , "postgres", "Batata2020");
                System.out.println("\nPostgreSQL conectado!\n");
            } catch (Exception e) {
                System.out.println("Conexão com PostgreSQL falhou. Erro: " + e.getMessage() + "\n");
            }
        }
        return conexao;
    }
}
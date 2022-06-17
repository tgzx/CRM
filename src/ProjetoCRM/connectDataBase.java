package ProjetoCRM;
//
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class connectDataBase {
    public static Connection conexao = null;
    public static Connection Conexao() {
        if (conexao != null) {
            return conexao;
        }
        if (MainBanco.enderecoIP.equals("remotemysql.com")){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/WsqcXz6l4e" +
                        "?autoReconnect=true&useSSL=false", "WsqcXz6l4e", "zbUBV2UeNU");
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                conexao = DriverManager.getConnection("jdbc:postgresql://192.168.1.9:5433/CRM_MeuBanco"
                        , "postgres", "Batata2020");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conexao;
    }
}
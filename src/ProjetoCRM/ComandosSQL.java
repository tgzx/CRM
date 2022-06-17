package ProjetoCRM;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComandosSQL {
    public static void truncate(String comando) {
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement(comando);
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void select(String comando) {
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement(comando);
            ResultSet resultado = select.executeQuery();
            boolean vazio = true;
            while(resultado.next()){
                Mercadoria.MostraNaTela(resultado);
                vazio = false;
            }
            if (vazio){
                System.out.println("VAZIO.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
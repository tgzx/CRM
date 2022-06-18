package ProjetoCRM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConstructCommands {
    public static void truncate(String comando) {
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement(comando);
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static ArrayList<Product> showData(String comando) {
        ArrayList<Product> produtos = new ArrayList<>();
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement(comando);
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                Product produto = ConstructSampler.MostraNaTela(resultado);
                produtos.add(produto);
            }
            if (produtos.isEmpty()){
                System.out.println("VAZIO.");
            }
        } catch (SQLException throwables) {
            System.out.println("Campo vazio");
            //throwables.printStackTrace();
        }
        return produtos;
    }
    public static void showDataV (String comando, int quantidade) {
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement(comando);
            ResultSet resultado = select.executeQuery();
            boolean vazio = true;
            while(resultado.next()){
                ConstructSampler.MostraNaTelaVenda(resultado, quantidade);
                vazio = false;
            }
            if (vazio){
                System.out.println("VAZIO.");
            }
        } catch (SQLException throwables) {
            System.out.println("Campo vazio");
            //throwables.printStackTrace();
        }
    }
    public static void giveData(String comando) {
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement(comando);
            select.execute();
        } catch (SQLException throwables) {
            System.out.println("Sem conexao com o banco.");
            //throwables.printStackTrace();
        }
    }
}
package ProjetoCRM;

import java.sql.*;
import java.util.Scanner;

public class BaseFunctions {

    public static void Texto(){
        System.out.println("O que deseja fazer agora?");
        System.out.println("1 - Vender");
        System.out.println("2 - Cadastrar Produtos");
        System.out.println("3 - Exibir Produtos");
        System.out.println("4 - Sair.");
        System.out.println("");
    }

    public static void TesteConexao(String enderecoIP){
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
            System.out.println("Conexao ok."); // Retorno de teste de conexão
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou."); // Retorno de teste de conexão
        }
    }

    public static void Vender(String enderecoIP, int quantidade, int codigoMercadoria){
        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;
        System.out.println("Digite o código da mercadoria: ");
        codigoMercadoria = scanner.nextInt();
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou."); // Retorno de teste de conexão
        }
        try {
            PreparedStatement select = conexao.prepareStatement("SELECT * FROM cadastro_mercadoria WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                System.out.println (resultado.getInt("codigo_mercadoria") + " - "
                        + resultado.getString("nome_mercadoria") + " - "
                        + resultado.getString("medida_mercadoria") + " - PRECO: "
                        + resultado.getString("preco_mercadoria") + " - ESTOQUE: "
                        + resultado.getFloat("estoqueAtual_mercadoria"));
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Digite a quantidade: ");
        quantidade = scanner.nextInt();

        // Define preço de venda após o cálcuco de quantidade no ato da venda.
        try {
            PreparedStatement select= conexao.prepareStatement("UPDATE cadastro_mercadoria SET precovendido_mercadoria = preco_mercadoria * "+quantidade+" WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Baixa o estoque da mercadoria ao concluir uma venda.
        try {
            PreparedStatement select = conexao.prepareStatement("UPDATE cadastro_mercadoria SET estoqueatual_mercadoria = estoqueatual_mercadoria - "+ quantidade +" WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Mostra detalhes da mercadoria cadastrada.
        try {
            PreparedStatement select = conexao.prepareStatement("SELECT * FROM cadastro_mercadoria WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                System.out.println("REALIZANDO VENDA..."); System.out.println("");
                System.out.println(resultado.getInt("codigo_mercadoria") + " - "
                        + resultado.getString("nome_mercadoria") + " -  "+quantidade+" "
                        + resultado.getString("medida_mercadoria") + " - PRECO: "
                        + resultado.getString("preco_mercadoria") + "R$ * "+quantidade+" = "
                        + resultado.getString("precoVendido_mercadoria") + "R$ - ESTOQUE: "
                        + resultado.getFloat("estoqueAtual_mercadoria"));
                System.out.println(""); System.out.println("VENDA FECHADA!");
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void Consultar(String enderecoIP){
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou.");
        }

        // Lista as mercadorias cadastradas no banco de dados.
        try {
            PreparedStatement select = conexao.prepareStatement("Select * from Cadastro_mercadoria");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                System.out.println(resultado.getInt("codigo_mercadoria") + " - "
                        + resultado.getString("nome_mercadoria") + " - "
                        + resultado.getString("medida_mercadoria") + " - PRECO: "
                        + resultado.getString("preco_mercadoria") + " - ESTOQUE: "
                        + resultado.getFloat("estoqueAtual_mercadoria"));
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void Cadastrar(String enderecoIP){
        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou.");
        }
        System.out.println("Insira a descricao da mercadoria (nome): ");
        String nomeM = scanner.next(); // Bugado: String nomeM = scanner.nextLine();
        System.out.println("Insira a medida (UN, MT, KG, etc) de " + nomeM + ":");
        String medidaM = scanner.next();
        System.out.println("Insira o preco de venda de " + nomeM + ":");
        float precoM = scanner.nextFloat();
        System.out.println("Insira o estoque de " + nomeM + ":");
        int estoqueM = scanner.nextInt();
        System.out.println("");

        // Cadastra mercadoria no banco de dados.
        try {
            PreparedStatement select = conexao.prepareStatement("INSERT INTO Cadastro_mercadoria(nome_mercadoria, medida_mercadoria, preco_mercadoria, estoqueAtual_mercadoria) " +
                    "VALUES ('" + nomeM + "','" + medidaM + "','" + precoM + "','" + estoqueM + "' );");
            select.execute();
            System.out.println("Inserido");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
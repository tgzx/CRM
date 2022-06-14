package ProjetoCRM;

import java.sql.*;

public class BancoFuncao {

    public static void Menu1(){
        System.out.println("O que deseja fazer agora?");
        System.out.println("1 - Vender");
        System.out.println("2 - Cadastrar Produtos");
        System.out.println("3 - Exibir Produtos");
        System.out.println("4 - Gerar Relatorio de Estoque em dinheiro");
        System.out.println("5 - Sair.");
        System.out.println("");
    }

    public static void Menu2(){
        System.out.println("Deseja listar em qual ordem?");
        System.out.println("1 - Codigo");
        System.out.println("2 - Preco");
        System.out.println("3 - Estoque");
        System.out.println("4 - Alfabetica");
        System.out.println("");
    }

    // ******************************* ESTABELECE CONEXAO COM O BANCO DE DADOS *****************************************
    public static Connection TesteConexao(String enderecoIP) throws SQLException {
        SQLFuncao.ComandoConectarBanco(enderecoIP);
        return SQLFuncao.ComandoConectarBanco(enderecoIP);
    }

    // ***************************** FUNCAO VENDA / MOSTRA PRECO / BAIXA ESTOQUE ***************************************
    public static void Vender(String enderecoIP) throws SQLException {
        SQLFuncao.ComandoVenderSQL(enderecoIP);
    }

    // *************************************** LISTA MERCADORIAS NA TELA ***********************************************
    public static void Consultar(String enderecoIP, String ordemMercadoria) throws SQLException {
        SQLFuncao.ComandoOrdenarMercadoriasSQL(enderecoIP, ordemMercadoria);
    }

    // **************************************** CADASTRO DE MERCADORIAS ************************************************
    public static void Cadastrar(String enderecoIP) throws SQLException {
        SQLFuncao.ComandoCadastrarSQL(enderecoIP);
    }

    // ********************************************** RELATORIOS *******************************************************
    public static void GerarRelatorio(String enderecoIP) throws SQLException {
        SQLFuncao.GerarRelatorio(enderecoIP);
    }
}
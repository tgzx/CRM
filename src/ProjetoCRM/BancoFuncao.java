package ProjetoCRM;

import java.sql.*;

public class BancoFuncao {


    public static void Menu1(){

        System.out.println("O que deseja fazer agora?");
        System.out.println("1 - Vender");
        System.out.println("2 - Cadastrar Produtos");
        System.out.println("3 - Exibir Produtos");
        System.out.println("4 - Gerar Relatorio de Movimentacao de Estoque");
        System.out.println("5 - Zerar banco de dados");
        System.out.println("6 - Sair");
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
    public static Connection TesteConexao() throws SQLException {
       connectDataBase.Conexao();
        return connectDataBase.Conexao();
    }

    // ***************************** FUNCAO VENDA / MOSTRA PRECO / BAIXA ESTOQUE ***************************************
    public static void Vender() throws SQLException {
        SQLFuncao.ComandoVenderSQL();
    }

    // *************************************** LISTA MERCADORIAS NA TELA ***********************************************
    public static void Consultar(String ordemMercadoria) throws SQLException {
        SQLFuncao.ComandoOrdenarMercadoriasSQL(ordemMercadoria);
    }

    // **************************************** CADASTRO DE MERCADORIAS ************************************************
    public static void Cadastrar() throws SQLException {
        SQLFuncao.ComandoCadastrarSQL();
    }

    // ********************************************** RELATORIOS *******************************************************
    public static void GerarRelatorio() throws SQLException {
        SQLFuncao.GerarRelatorio();
    }

    // ******************************************** ZERA O BANCO *******************************************************
    public static void ZerarBanco() throws SQLException {
        SQLFuncao.ComandoZerarBanco();
    }
}
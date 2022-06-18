package ProjetoCRM;

import java.sql.*;

public class BaseFunctions {


    public static void Menu1(){

        System.out.println("O que deseja fazer agora?");
        System.out.println("1 - Vender");
        System.out.println("2 - Cadastrar Produtos");
        System.out.println("3 - Exibir Produtos");
        System.out.println("4 - Relatorios");
        System.out.println("5 - Atualizar cadastros");
        System.out.println("6 - Zerar banco de dados");
        System.out.println("7 - Sair");
        System.out.println("");
    }

    public static void Menu2(){
        System.out.println("Deseja listar em qual ordem?");
        System.out.println("1 - Codigo");
        System.out.println("2 - Preco");
        System.out.println("3 - Estoque");
        System.out.println("4 - Alfabetica");
        System.out.println("5 - Voltar.");
        System.out.println("");
    }

    public static void Menu3(){
        System.out.println("Deseja visualizar qual relatorio?");
        System.out.println("1 - Movimentacao de Estoque");
        System.out.println("2 - Ultima venda");
        System.out.println("3 - Voltar.");
        System.out.println("");
    }

    // ******************************* ESTABELECE CONEXAO COM O BANCO DE DADOS *****************************************
    public static Connection TesteConexao() throws SQLException {
       ConnectSQL.Conexao();
        return ConnectSQL.Conexao();
    }

    // ***************************** FUNCAO VENDA / MOSTRA PRECO / BAIXA ESTOQUE ***************************************
    public static void Vender() throws SQLException {
        CommandsSQL.ComandoVenderSQL();
    }

    // *************************************** LISTA MERCADORIAS NA TELA ***********************************************
    public static void Listar(String ordemMercadoria) throws SQLException {
        CommandsSQL.ComandoOrdenarMercadoriasSQL(ordemMercadoria);
    }

    // *************************************** CONSULTA MERCADORIA *****************************************************
    public static void Atualizar() throws SQLException {
        CommandsSQL.ComandoAtualizarMercadoriaSQL();
    }

    // **************************************** CADASTRO DE MERCADORIAS ************************************************
    public static void Cadastrar() throws SQLException {
        CommandsSQL.ComandoCadastrarSQL();
    }

    // ****************************************** RELATORIOS ESTOQUE ***************************************************
    public static void relatorioMovimentacao() throws SQLException {
        CommandsSQL.GerarRelatorio();
    }

    // ****************************************** RELATORIOS VENDAS ****************************************************
    public static void relatorioVenda() throws SQLException {
        CommandsSQL.lastSell();
    }

    // ******************************************** ZERA O BANCO *******************************************************
    public static void ZerarBanco() throws SQLException {
        CommandsSQL.ComandoZerarBanco();
    }
}
package ProjetoCRM;

import java.sql.*;
import java.util.Scanner;

public class SQLFuncao {
    public static Connection conexao = null;
    public static Connection ComandoConectarBanco (String enderecoIP) throws  SQLException{
        if (conexao != null){
            return conexao;
        }

        // *********************************************** MYSQL *******************************************************
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://" + enderecoIP + ":3306/WsqcXz6l4e?autoReconnect=true&useSSL=false",
                    "WsqcXz6l4e","zbUBV2UeNU"); // Banco: CRM_MeuBanco login: postgres | password: Batata2020
            //System.out.println("Conectado."); // Retorno de teste de conexão
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou."); // Retorno de teste de conexão
        }
        return conexao;


        // ******************************************** POSTGRESQL *****************************************************
        /*
        }
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco",
                    "postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres | password: Batata2020
            //System.out.println("Conectado."); // Retorno de teste de conexão
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou."); // Retorno de teste de conexão
        }
        return conexao;*/
    }

    public  static void ComandoZerarBanco (String enderecoIP) throws  SQLException{
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("truncate " +
                    "cadastro_mercadoria restart identity;");
            select.execute();
            System.out.println("Banco Zerado!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void ComandoCadastrarSQL (String enderecoIP) throws SQLException {
        Mercadoria novoProduto = Mercadoria.Cadastrar();
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("INSERT INTO " +
                    "cadastro_mercadoria(nome_mercadoria, medida_mercadoria, preco_mercadoria, " +
                    "estoqueatual_mercadoria) " + "VALUES ('" + novoProduto.nomeMercadoria + "','"
                    + novoProduto.medidaMercadoria + "'," + novoProduto.precoMercadoria + ","
                    + novoProduto.estoqueAtualMercadoria + " );");
            select.execute();
            System.out.println("Inserido");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Cria ou atualiza o valor (dinheiro) em estoque da mercadoria.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("UPDATE " +
                    "cadastro_mercadoria SET valoratual_movimentacao = estoqueatual_mercadoria * preco_mercadoria ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void ComandoOrdenarMercadoriasSQL(String enderecoIP, String ordemMercadorias) throws SQLException {
        // Reorganiza mercadorias por codigo de mercadoria, ordem crescente (crescente ASC, descrescente DESC).
        if (ordemMercadorias.equals("1")){
            try {
                PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * FROM " +
                        "cadastro_mercadoria ORDER BY codigo_mercadoria ASC;");
                ResultSet resultado = select.executeQuery();
                while(resultado.next()){
                    Mercadoria.MostraNaTela(resultado);
                }
                System.out.println("");
                System.out.println("Mercadorias listadas por codigo");
                System.out.println("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (ordemMercadorias.equals("2")){
            try {
                PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * FROM " +
                        "cadastro_mercadoria ORDER BY preco_mercadoria ASC;");
                ResultSet resultado = select.executeQuery();
                while(resultado.next()){
                    Mercadoria.MostraNaTela(resultado);
                }
                System.out.println("");
                System.out.println("Mercadorias listadas por maior preco.");
                System.out.println("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (ordemMercadorias.equals("3")){
            try {
                PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * FROM " +
                        "cadastro_mercadoria ORDER BY estoqueatual_mercadoria ASC;");
                ResultSet resultado = select.executeQuery();
                while(resultado.next()){
                    Mercadoria.MostraNaTela(resultado);
                }
                System.out.println("");
                System.out.println("Mercadorias listadas por maior estoque.");
                System.out.println("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * FROM " +
                        "cadastro_mercadoria ORDER BY nome_mercadoria ASC;");
                ResultSet resultado = select.executeQuery();
                while(resultado.next()){
                    Mercadoria.MostraNaTela(resultado);
                }
                System.out.println("");
                System.out.println("Mercadorias listadas por ordem alfabetica.");
                System.out.println("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void ComandoVenderSQL (String enderecoIP) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o código da mercadoria: ");
        int codigoMercadoria = scanner.nextInt();
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * " +
                    "FROM cadastro_mercadoria WHERE codigo_mercadoria = " + codigoMercadoria + " ");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                Mercadoria.MostraNaTela(resultado);
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Digite a quantidade: ");
        int quantidade = scanner.nextInt();

        // Calcula preco de venda da mercadoria (preco de venda * quantidade).
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT " +
                    "preco_mercadoria FROM cadastro_mercadoria " +
                    "WHERE codigo_mercadoria = " + codigoMercadoria * quantidade + " ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Baixa o estoque da mercadoria ao concluir uma venda.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("UPDATE " +
                    "cadastro_mercadoria SET estoqueatual_mercadoria = estoqueatual_mercadoria - " + quantidade + " " +
                    "WHERE codigo_mercadoria = " + codigoMercadoria + " ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Cria ou atualiza o valor (dinheiro) em estoque da mercadoria.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("UPDATE " +
                    "cadastro_mercadoria SET valoratual_movimentacao = estoqueatual_mercadoria * preco_mercadoria " +
                    "WHERE codigo_mercadoria = " + codigoMercadoria + " ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Mostra detalhes da venda da mercadoria selecionada.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * " +
                    "FROM cadastro_mercadoria WHERE codigo_mercadoria = " + codigoMercadoria + " ");
            ResultSet resultado = select.executeQuery();
            System.out.println("REALIZANDO VENDA..."); System.out.println("");
            while(resultado.next()){
                Mercadoria.MostraNaTelaVenda(resultado, quantidade);
            }
            System.out.println(""); System.out.println("VENDA FECHADA!");
            System.out.println();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void GerarRelatorio(String enderecoIP) throws SQLException {
        Estoque novoProduto = new Estoque();

        // Calcula e salva nas variaveis declaradas na classe Estoque os dados para a insercao
        // no banco de dados e geracao do relatorio.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT " +
                    "SUM(valoratual_movimentacao) AS valoratual_movimentacao FROM cadastro_mercadoria WHERE " +
                    "(estoqueatual_mercadoria > 0);");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                novoProduto.valorEstoque = resultado.getFloat("valoratual_movimentacao");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT " +
                    "SUM(estoqueatual_mercadoria) AS volume_movimentacao FROM cadastro_mercadoria " +
                    "WHERE (estoqueatual_mercadoria > 0);");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                novoProduto.volumeatualEstoque = resultado.getFloat("volume_movimentacao");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT " +
                    "COUNT(nome_mercadoria) AS mercadoria_cadastrada FROM cadastro_mercadoria;");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                novoProduto.qtdMercadoria = resultado.getInt("mercadoria_cadastrada");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Insere as informacoes coletadas no banco de dados e gera um relatorio com essas informacoes.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("INSERT INTO " +
                    "movimentacao_mercadoria (valoratual_movimentacao, volumeatual_movimentacao, " +
                    "mercadoria_cadastrada) VALUES (" + novoProduto.valorEstoque + ", " + novoProduto.volumeatualEstoque
                    + ", " + novoProduto.qtdMercadoria + ");");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = BancoFuncao.TesteConexao(enderecoIP).prepareStatement("SELECT * FROM" +
                    " movimentacao_mercadoria WHERE cod_movimentacao = (SELECT MAX(cod_movimentacao) " +
                    "FROM movimentacao_mercadoria)");
            ResultSet resultado = select.executeQuery();
            System.out.println("*********************************************************************************" +
                    "*******\n");
            System.out.println("MOVIMENTACAO ESTOQUE"); System.out.println("");
            while(resultado.next()){
                Mercadoria.MostraNaTelaRelatorio(resultado);
            }
            System.out.println(""); System.out.println("*********************************************************" +
                    "*******************************\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // **************************** CONSULTA DE SINTAXE DE COMANDOS PARA POSTGRESQL ***********************************
    // *** INSERT INTO movimentacao_mercadoria (SELECT SUM(preco_mercadoria) AS soma FROM cadastro_mercadoria);
    //
    // *** INSERT INTO movimentacao_mercadoria (valoratual_movimentacao) (SELECT SUM(preco_mercadoria)
    // AS soma FROM cadastro_mercadoria);
    //
    // *** INSERT INTO movimentacao_mercadoria (valoratual_movimentacao) VALUES (2000);
    //
    // *** SELECT * FROM movimentacao_mercadoria WHERE cod_movimentacao = (SELECT MAX(cod_movimentacao)
    // FROM movimentacao_mercadoria) pega o ultimo registro informado
}
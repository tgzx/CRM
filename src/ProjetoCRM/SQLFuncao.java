package ProjetoCRM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLFuncao {

    public  static void ComandoZerarBanco (){
        if(MainBanco.enderecoIP.equals("remotemysql.com")){
            ComandosSQL.truncate("TRUNCATE TABLE cadastro_mercadoria;");
        } else {
            ComandosSQL.truncate("truncate cadastro_mercadoria restart identity;");
        }
    }
    
    public static void ComandoCadastrarSQL (){
        Mercadoria novoProduto = Mercadoria.Cadastrar();
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("INSERT INTO " +
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
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("UPDATE " +
                    "cadastro_mercadoria SET valoratual_movimentacao = estoqueatual_mercadoria * preco_mercadoria ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void ComandoOrdenarMercadoriasSQL(String ordemMercadorias){
        if (ordemMercadorias.equals("1")){
            ComandosSQL.select("SELECT * FROM cadastro_mercadoria ORDER BY codigo_mercadoria ASC;");
        } else if (ordemMercadorias.equals("2")){
            ComandosSQL.select("SELECT * FROM cadastro_mercadoria ORDER BY preco_mercadoria ASC;");
        } else if (ordemMercadorias.equals("3")){
            ComandosSQL.select("SELECT * FROM cadastro_mercadoria ORDER BY estoqueatual_mercadoria ASC;");
        } else {
            ComandosSQL.select("SELECT * FROM cadastro_mercadoria ORDER BY nome_mercadoria ASC;");
        }
    }

    public static void ComandoVenderSQL (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o código da mercadoria: ");
        int codigoMercadoria = scanner.nextInt();
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT * " +
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
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT " +
                    "preco_mercadoria FROM cadastro_mercadoria " +
                    "WHERE codigo_mercadoria = " + codigoMercadoria * quantidade + " ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Baixa o estoque da mercadoria ao concluir uma venda.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("UPDATE " +
                    "cadastro_mercadoria SET estoqueatual_mercadoria = estoqueatual_mercadoria - " + quantidade + " " +
                    "WHERE codigo_mercadoria = " + codigoMercadoria + " ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Cria ou atualiza o valor (dinheiro) em estoque da mercadoria.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("UPDATE " +
                    "cadastro_mercadoria SET valoratual_movimentacao = estoqueatual_mercadoria * preco_mercadoria " +
                    "WHERE codigo_mercadoria = " + codigoMercadoria + " ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Mostra detalhes da venda da mercadoria selecionada.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT * " +
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

    public static void GerarRelatorio (){
        Estoque novoProduto = new Estoque();

        // Calcula e salva nas variaveis declaradas na classe Estoque os dados para a insercao
        // no banco de dados e geracao do relatorio.
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT " +
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
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT " +
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
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT " +
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
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("INSERT INTO " +
                    "movimentacao_mercadoria (valoratual_movimentacao, volumeatual_movimentacao, " +
                    "mercadoria_cadastrada) VALUES (" + novoProduto.valorEstoque + ", " + novoProduto.volumeatualEstoque
                    + ", " + novoProduto.qtdMercadoria + ");");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = BancoFuncao.TesteConexao().prepareStatement("SELECT * FROM" +
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
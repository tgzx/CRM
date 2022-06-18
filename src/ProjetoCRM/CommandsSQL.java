package ProjetoCRM;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

public class CommandsSQL {
    public  static void ComandoZerarBanco (){
        if(MainBanco.enderecoIP.equals("remotemysql.com")){
            ConstructCommands.truncate("TRUNCATE TABLE cadastro_mercadoria;");
            ConstructCommands.truncate("TRUNCATE TABLE movimentacao_mercadoria;");
        } else {
            ConstructCommands.truncate("truncate cadastro_mercadoria restart identity;");
            ConstructCommands.truncate("truncate movimentacao_mercadoria restart identity;");
        }
    }
    
    public static void ComandoCadastrarSQL (){
        Insert.giveData("INSERT INTO cadastro_mercadoria(" + Insert.columns() + ") VALUES ("+ Insert.values());
        Insert.giveData("UPDATE cadastro_mercadoria SET valoratual_movimentacao = "
                + "estoqueatual_mercadoria * preco_mercadoria ");
    }

    public static void ComandoAtualizarMercadoriaSQL(){
        Insert.giveData("UPDATE cadastro_mercadoria SET " + Insert.columnsValuesUpdt());
    }

    public static void ComandoOrdenarMercadoriasSQL(String ordemMercadorias){
        if (ordemMercadorias.equals("1")){
            ConstructCommands.showData("SELECT * FROM cadastro_mercadoria "
                    + "ORDER BY codigo_mercadoria ASC;");
        } else if (ordemMercadorias.equals("2")){
            ConstructCommands.showData("SELECT * FROM cadastro_mercadoria "
                    + "ORDER BY preco_mercadoria ASC;");
        } else if (ordemMercadorias.equals("3")){
            ConstructCommands.showData("SELECT * FROM cadastro_mercadoria "
                    + "ORDER BY estoqueatual_mercadoria ASC;");
        } else {
            ConstructCommands.showData("SELECT * FROM cadastro_mercadoria "
                    + "ORDER BY nome_mercadoria ASC;");
        }
    }

    public static void lastSell(){
        System.out.println(ConstructSampler.lastSell);
    }

    public static void ComandoVenderSQL (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o código da mercadoria: (ou digite '0' para voltar ao menu principal.)");
        int codigoMercadoria = scanner.nextInt();
        if (codigoMercadoria != 0){
            // Mostra a mercadoria selecionada na tela.
            Insert.showData("SELECT * FROM cadastro_mercadoria WHERE codigo_mercadoria = "
                    + codigoMercadoria + " ");

            System.out.println("Digite a quantidade: ");
            int quantidade = scanner.nextInt();

            // Calcula preco de venda da mercadoria (preco de venda * quantidade).
            Insert.giveData("SELECT preco_mercadoria FROM cadastro_mercadoria WHERE codigo_mercadoria = "
                    + codigoMercadoria * quantidade + " ");

            // Baixa o estoque da mercadoria ao concluir uma venda.
            Insert.giveData("UPDATE cadastro_mercadoria SET estoqueatual_mercadoria = "
                    + "estoqueatual_mercadoria - " + quantidade + " WHERE codigo_mercadoria = "
                    + codigoMercadoria + " ");

            // Cria ou atualiza o valor (dinheiro) em estoque da mercadoria.
            Insert.giveData("UPDATE cadastro_mercadoria SET valoratual_movimentacao = "
                    + "estoqueatual_mercadoria * preco_mercadoria WHERE codigo_mercadoria = " + codigoMercadoria + " ");

            // Mostra detalhes da venda da mercadoria selecionada.
            Insert.showDataV("SELECT * " + "FROM cadastro_mercadoria WHERE codigo_mercadoria = "
                    + codigoMercadoria + " ", quantidade);
        }
    }

    public static void GerarRelatorio (){
        Estoque novoProduto = new Estoque();

        // Calcula e salva nas variaveis declaradas na classe Estoque os dados para a insercao
        // no banco de dados e geracao do relatorio.
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement("SELECT " +
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
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement("SELECT " +
                    "SUM(estoqueatual_mercadoria) AS volume_movimentacao FROM cadastro_mercadoria " +
                    "WHERE (estoqueatual_mercadoria > 0);");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                novoProduto.volumeatualEstoque = resultado.getInt("volume_movimentacao");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement("SELECT " +
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
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement("INSERT INTO " +
                    "movimentacao_mercadoria (valoratual_movimentacao, volumeatual_movimentacao, " +
                    "mercadoria_cadastrada) VALUES (" + novoProduto.valorEstoque + ", " + novoProduto.volumeatualEstoque
                    + ", " + novoProduto.qtdMercadoria + ");");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = BaseFunctions.TesteConexao().prepareStatement("SELECT * FROM" +
                    " movimentacao_mercadoria WHERE cod_movimentacao = (SELECT MAX(cod_movimentacao) " +
                    "FROM movimentacao_mercadoria)");
            ResultSet resultado = select.executeQuery();
            System.out.println("*********************************************************************************" +
                    "*******\n");
            System.out.println("MOVIMENTACAO ESTOQUE"); System.out.println("");
            while(resultado.next()){
                ConstructSampler.MostraNaTelaRelatorio(resultado);
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
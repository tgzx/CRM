package ProjetoCRM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Mercadoria {
    public int codigoMercadoria;
    public String nomeMercadoria;
    public String medidaMercadoria;
    public float precoMercadoria;
    public float estoqueAtualMercadoria;

    public static Mercadoria Cadastrar(){
        Scanner scanner = new Scanner(System.in);
        Mercadoria novoProduto = new Mercadoria();
        System.out.println("Insira a descricao da mercadoria (nome): ");
        novoProduto.nomeMercadoria = scanner.next(); // Bugado: String nomeM = scanner.nextLine();
        System.out.println("Insira a medida (UN, MT, KG, etc) de " + novoProduto.nomeMercadoria + ":");
        novoProduto.medidaMercadoria = scanner.next();
        System.out.println("Insira o preco de venda de " + novoProduto.nomeMercadoria + ":");
        novoProduto.precoMercadoria = scanner.nextFloat();
        System.out.println("Insira o estoque de " + novoProduto.nomeMercadoria + ":");
        novoProduto.estoqueAtualMercadoria = scanner.nextInt();
        return novoProduto;
    }

    public static void MostraNaTela(ResultSet resultado) throws SQLException {
        Mercadoria novoProduto = new Mercadoria();
        novoProduto.codigoMercadoria = resultado.getInt("codigo_mercadoria");
        novoProduto.nomeMercadoria = resultado.getString("nome_mercadoria");
        novoProduto.precoMercadoria = resultado.getFloat("preco_mercadoria");
        novoProduto.medidaMercadoria = resultado.getString("medida_mercadoria");
        novoProduto.estoqueAtualMercadoria = resultado.getFloat("estoqueatual_mercadoria");
        System.out.println("[COD-" + novoProduto.codigoMercadoria + "] - MERCADORIA: [" + Espacamento.larguraLimite(novoProduto.nomeMercadoria)
                + "] [ " + Espacamento.larguraLimiteEstoque(novoProduto.estoqueAtualMercadoria) + "  " + Espacamento.larguraLimiteMedida(novoProduto.medidaMercadoria) + " ] PRECO: ["
                + Espacamento.larguraLimiteEstoque(novoProduto.precoMercadoria) + " R$] ");
    }

    public static void MostraNaTelaVenda(ResultSet resultado, int quantidade) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Mercadoria novoProduto = new Mercadoria();
        novoProduto.codigoMercadoria = resultado.getInt("codigo_mercadoria");
        novoProduto.nomeMercadoria = resultado.getString("nome_mercadoria");
        novoProduto.precoMercadoria = resultado.getFloat("preco_mercadoria");
        novoProduto.medidaMercadoria = resultado.getString("medida_mercadoria");
        novoProduto.estoqueAtualMercadoria = resultado.getFloat("estoqueatual_mercadoria");
        System.out.println("[COD-"+ novoProduto.codigoMercadoria + "] [" + quantidade + " x "
                + novoProduto.nomeMercadoria + "] VALOR A PAGAR: [" + (novoProduto.precoMercadoria * quantidade)
                + "R$] ");
        System.out.println("Digite 'ok' para continuar...");
        scanner.next();
        System.out.println("Estoque atualizado: [COD-" + novoProduto.codigoMercadoria + "] "
                + novoProduto.nomeMercadoria + " [" + novoProduto.estoqueAtualMercadoria + " "
                + novoProduto.medidaMercadoria + "] [PRECO: " + novoProduto.precoMercadoria + "R$]");
    }

    public static void MostraNaTelaRelatorio(ResultSet resultado) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ' às ' h:mm a");
        Date date = new Date();
        Estoque novoProduto = new Estoque();
        novoProduto.valorEstoque = resultado.getFloat("valoratual_movimentacao");
        novoProduto.volumeatualEstoque = resultado.getFloat("volumeatual_movimentacao");
        novoProduto.qtdMercadoria = resultado.getInt("mercadoria_cadastrada");
        System.out.println("Relatorio gerado em: " + formatter.format(date) + "\n\nVolume do Estoque: ["
                + novoProduto.volumeatualEstoque + "] Valor em estoque: [" + novoProduto.valorEstoque +
                "R$] Mercadorias Cadastradas: ["+ novoProduto.qtdMercadoria +"]");
    }
}
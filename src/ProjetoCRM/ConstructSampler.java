package ProjetoCRM;

import javafx.scene.input.InputMethodTextRun;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConstructSampler {
    public static String lastSell = "Não foi realizado nenhuma venda desde que o programa foi inciado.";

    public static String widthLimit(String texto) {
        int cont = texto.length();
        while (cont < 10) {
            for (int i = 0; i < (10 - texto.length()); i++) {
                if (i > 0) {
                    texto = " " + texto;
                }
                texto = texto + " ";
            }
            cont++;
        }
        return texto;
    }

    public static String widthLimitValor(Float numero) {
        String texto = String.valueOf(numero);
        int cont = 0;
        while (cont < 5) {
            for (int i = 0; i < (5 - texto.length()); i++) {
                texto = texto + " ";
            }
            cont++;
        }
        return texto;
    }

    public static String widthLimitMedida(String texto) {
        int cont = 0;
        while (cont < 5) {
            for (int i = 0; i < (5 - texto.length()); i++) {
                texto = " " + texto;
            }
            cont++;
        }
        return texto;
    }

    public static Product MostraNaTela(ResultSet resultado) throws SQLException {
        Product novoProduto = new Product();
        novoProduto.codigoMercadoria = resultado.getInt("codigo_mercadoria");
        novoProduto.nomeMercadoria = resultado.getString("nome_mercadoria");
        novoProduto.precoMercadoria = resultado.getFloat("preco_mercadoria");
        novoProduto.medidaMercadoria = resultado.getString("medida_mercadoria");
        novoProduto.estoqueAtualMercadoria = resultado.getFloat("estoqueatual_mercadoria");
        System.out.println("[COD-" + novoProduto.codigoMercadoria + "] - MERCADORIA: ["
                + ConstructSampler.widthLimit(novoProduto.nomeMercadoria)
                + "] [ " + ConstructSampler.widthLimitValor(novoProduto.estoqueAtualMercadoria)
                + "  " + ConstructSampler.widthLimitMedida(novoProduto.medidaMercadoria) + " ] PRECO: ["
                + ConstructSampler.widthLimitValor(novoProduto.precoMercadoria) + " R$] ");
        return novoProduto;
    }

    public static void MostraNaTelaVenda(ResultSet resultado, int quantidade) throws SQLException {
        DecimalFormat df = new DecimalFormat("#.##");                               // formata float '0,00'
        Scanner scanner = new Scanner(System.in);
        Product novoProduto = new Product();
        novoProduto.codigoMercadoria = resultado.getInt("codigo_mercadoria");
        novoProduto.nomeMercadoria = resultado.getString("nome_mercadoria");
        novoProduto.precoMercadoria = resultado.getFloat("preco_mercadoria");
        novoProduto.medidaMercadoria = resultado.getString("medida_mercadoria");
        novoProduto.estoqueAtualMercadoria = resultado.getFloat("estoqueatual_mercadoria");

        String precoFormatado = df.format(novoProduto.precoMercadoria * quantidade); // formata float '0,00'

        System.out.println("[COD-" + novoProduto.codigoMercadoria + "] [" + quantidade + " x "
                + novoProduto.nomeMercadoria + "] VALOR A PAGAR: [" + precoFormatado
                + "R$] ");
        System.out.println("Digite 'ok' para continuar...\n");
        scanner.next();
        lastSell = "*********************************************************************************" +
                "*******\n" + "ULTIMA VENDA REALIZADA\n" + "[COD-" + novoProduto.codigoMercadoria + "] ["
                + quantidade + " x " + novoProduto.nomeMercadoria + "] VALOR PAGO: [" + precoFormatado
                + "R$] " + "\n*********************************************************" +
                "*******************************\n";
        /*lastSell = "Estoque atualizado: [COD-" + novoProduto.codigoMercadoria + "] "
                + novoProduto.nomeMercadoria + " [" + novoProduto.estoqueAtualMercadoria + " "
                + novoProduto.medidaMercadoria + "] [PRECO: " + novoProduto.precoMercadoria + "R$]";*/
    }

    public static void MostraNaTelaRelatorio(ResultSet resultado) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ' às ' h:mm a");
        Date date = new Date();
        Estoque novoProduto = new Estoque();
        novoProduto.valorEstoque = resultado.getFloat("valoratual_movimentacao");
        novoProduto.volumeatualEstoque = resultado.getInt("volumeatual_movimentacao");
        novoProduto.qtdMercadoria = resultado.getInt("mercadoria_cadastrada");
        System.out.println("Relatorio gerado em: " + formatter.format(date) + "\n\nVolume do Estoque: ["
                + novoProduto.volumeatualEstoque + "] Valor em estoque: [" + novoProduto.valorEstoque +
                "R$] Mercadorias Cadastradas: [" + novoProduto.qtdMercadoria + "]");
    }

}
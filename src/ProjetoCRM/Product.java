package ProjetoCRM;

import java.util.ArrayList;
import java.util.Scanner;

public class Product {
    public int codigoMercadoria;
    public String nomeMercadoria;
    public String medidaMercadoria;
    public float precoMercadoria;
    public float estoqueAtualMercadoria;

    public static Product cadastrar(){
        Scanner scanner = new Scanner(System.in);
        Product novoProduto = new Product();
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

    public static Product atualizar(){
        Scanner scanner = new Scanner(System.in);
        Product novoProduto = new Product();
        System.out.println("Insira o codigo da mercadoria que sera atualizada: (ou digite '0' "
                + "para voltar para o menu principal.)");
        novoProduto.codigoMercadoria = scanner.nextInt();
        if (novoProduto.codigoMercadoria != 0){
            ArrayList<Product> products = ConstructCommands.showData("SELECT * FROM cadastro_mercadoria "
                    + "WHERE codigo_mercadoria = " + novoProduto.codigoMercadoria);
            System.out.println("Nova descricao de " + products.get(0).nomeMercadoria + ": ");
            novoProduto.nomeMercadoria = scanner.next(); // Bugado: String nomeM = scanner.nextLine();
            System.out.println("Nova medida (UN, MT, KG, etc) de " + products.get(0).nomeMercadoria + ":");
            novoProduto.medidaMercadoria = scanner.next();
            System.out.println("Novo preco de venda de " + products.get(0).nomeMercadoria + ":");
            novoProduto.precoMercadoria = scanner.nextFloat();
            System.out.println("Novo estoque de " + products.get(0).nomeMercadoria + ":");
            novoProduto.estoqueAtualMercadoria = scanner.nextInt();
        }
        return novoProduto;
    }
}
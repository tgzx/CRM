package ProjetoCRM;

public class Insert extends ConstructCommands {
    public static String columns(){
        return "nome_mercadoria, medida_mercadoria, preco_mercadoria, estoqueatual_mercadoria";
    }
    public static String values() {
        Product novoProduto = Product.cadastrar();
        return "'" + novoProduto.nomeMercadoria + "', '"+ novoProduto.medidaMercadoria + "',"
                +  novoProduto.precoMercadoria + ", " + novoProduto.estoqueAtualMercadoria +");";
    }
    public static String columnsValuesUpdt(){
        Product novoProduto = Product.atualizar();
        return "nome_mercadoria = '" + novoProduto.nomeMercadoria + "', preco_mercadoria = "
                + novoProduto.precoMercadoria + ", estoqueatual_mercadoria = " + novoProduto.estoqueAtualMercadoria
                + " WHERE codigo_mercadoria =" + novoProduto.codigoMercadoria + ";";
    }
}
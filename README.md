# Projeto CRM (Estático) - EM CONSTRUÇÃO
## Baixe e faça um teste na sua própria máquina! O programa está sempre sendo atualizado.
### Links de download
#### Programa em JAVA: https://github.com/tgzx/CRM/raw/master/out/artifacts/CRM_jar/CRM.jar
#### Executável: https://mega.nz/file/XiBxXRrC#2mr_TpG_6xdRyab2Jwn-2leG6Gdeoico2GEJirSvMEQ

OBS.: Coloque os 2 arquivos na mesma pasta e execute o "Executar.bat".
Fique a vontade para dar sugestões e fazer críticas construtivas!

# Projeto (Desatualizado | Veja o código atualizado em: SRC/ProjetoCRM)
### ProjetoCRM
O projeto consiste em construir um programa "simples" em que seja possível gerir atividades básicas de uma empresa (cadastrar produtos, consultar produtos, vender, etc.) a partir de conceitos básicos de programação orientada a objetos nas linguagens JAVA e PostgreSQL.

### Finalidade
Atestar meus conhecimentos nas linguagens citadas para a entrada no mercado de trabalho como programador trainee.

### Código
Código principal reduzido aplicando algumas das boas práticas na programação: clean code e documentação. Utilizando-se de funções (classes) e métodos:

```java
import java.util.Scanner;
public class MainBanco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String enderecoIP = "192.168.1.9"; // Endereco do servidor (SQL) [localhost ou ip] pode ser alterado em "pg_hba.conf"
        boolean loop = false;
        BaseFunctions.TesteConexao(enderecoIP);
        System.out.println("Selecione uma das opções abaixo: ");
        while (loop == false){
            BaseFunctions.Texto();
            String resposta = scanner.next(); System.out.println();
            if (resposta.equals("1")){
                BaseFunctions.Vender(enderecoIP,0,0);
                loop = false;
            } else if (resposta.equals("2")){
                BaseFunctions.Cadastrar(enderecoIP);
                loop = false;
            } if (resposta.equals("3")){
                BaseFunctions.Consultar(enderecoIP);
                loop = false;
            } else if (resposta.equals("4")){
                loop = true;
            } else {
                System.out.println("Selecione apenas uma das opcoes citadas.");
                loop = false;
            }
        }
    }
}
```

### Função (Classe) e Métodos utilizados
Abaixo construí os métodos que definem as ações que serão utilizadas pelo usuário no código principal (código acima):

```java
import java.sql.*;
import java.util.Scanner;

public class BaseFunctions {
    public static void Texto(){
        System.out.println("O que deseja fazer agora?");
        System.out.println("1 - Vender");
        System.out.println("2 - Cadastrar Produtos");
        System.out.println("3 - Exibir Produtos");
        System.out.println("4 - Sair.");
        System.out.println("");
    }
    public static void TesteConexao(String enderecoIP){
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
            System.out.println("Conexao ok."); // Retorno de teste de conexão
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou."); // Retorno de teste de conexão
        }
    }
    public static void Vender(String enderecoIP, int quantidade, int codigoMercadoria){
        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;
        System.out.println("Digite o código da mercadoria: ");
        codigoMercadoria = scanner.nextInt();
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou."); // Retorno de teste de conexão
        }
        try {
            PreparedStatement select = conexao.prepareStatement("SELECT * FROM cadastro_mercadoria WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                System.out.println (resultado.getInt("codigo_mercadoria") + " - "
                        + resultado.getString("nome_mercadoria") + " - "
                        + resultado.getString("medida_mercadoria") + " - PRECO: "
                        + resultado.getString("preco_mercadoria") + " - ESTOQUE: "
                        + resultado.getFloat("estoqueAtual_mercadoria"));
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Digite a quantidade: ");
        quantidade = scanner.nextInt();
        try {
            PreparedStatement select = conexao.prepareStatement("UPDATE cadastro_mercadoria SET estoqueatual_mercadoria = estoqueatual_mercadoria - "+ quantidade +" WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            select.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement select = conexao.prepareStatement("SELECT * FROM cadastro_mercadoria WHERE codigo_mercadoria = "+codigoMercadoria+" ");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                System.out.println("REALIZANDO VENDA..."); System.out.println("");
                System.out.println(resultado.getInt("codigo_mercadoria") + " - "
                        + resultado.getString("nome_mercadoria") + " -  "+quantidade+" "
                        + resultado.getString("medida_mercadoria") + " - PRECO: "
                        + resultado.getString("preco_mercadoria") + " * "+quantidade+" - ESTOQUE: "
                        + resultado.getFloat("estoqueAtual_mercadoria"));
                System.out.println(""); System.out.println("VENDA FECHADA!");
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void Consultar(String enderecoIP){
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
            //System.out.println("Conexao ok."); // Retorno de teste de conexão
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou.");
        }
        try {
            PreparedStatement select = conexao.prepareStatement("Select * from Cadastro_mercadoria");
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                System.out.println(resultado.getInt("codigo_mercadoria") + " - "
                        + resultado.getString("nome_mercadoria") + " - "
                        + resultado.getString("medida_mercadoria") + " - PRECO: "
                        + resultado.getString("preco_mercadoria") + " - ESTOQUE: "
                        + resultado.getFloat("estoqueAtual_mercadoria"));
            }
            System.out.println("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void Cadastrar(String enderecoIP){
        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://" + enderecoIP + ":5433/CRM_MeuBanco","postgres","Batata2020"); // Banco: CRM_MeuBanco login: postgres || password: Batata2020
            //System.out.println("Conexao ok."); // Retorno de teste de conexão
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Conexao falhou.");
        }
        System.out.println("Insira a descricao da mercadoria (nome): ");
        String nomeM = scanner.next(); // String nomeM = scanner.nextLine();
        System.out.println("Insira a medida (UN, MT, KG, etc) de " + nomeM + ":");
        String medidaM = scanner.next();
        System.out.println("Insira o preco de venda de " + nomeM + ":");
        float precoM = scanner.nextFloat();
        System.out.println("Insira o estoque de " + nomeM + ":");
        int estoqueM = scanner.nextInt();
        System.out.println("");

        try {
            PreparedStatement select = conexao.prepareStatement("INSERT INTO Cadastro_mercadoria(nome_mercadoria, medida_mercadoria, preco_mercadoria, estoqueAtual_mercadoria) " +
                    "VALUES ('" + nomeM + "','" + medidaM + "','" + precoM + "','" + estoqueM + "' );");
            select.execute();
            System.out.println("Inserido");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
```
### Comentários de TGZX
O programa ainda está em desenvolvimento e sempre sendo atualizado. O código acima pode não representar o estado atual do código, todavia ainda pode-se utilizar como comprovação de conhecimento prático na linaguagem JAVA e linguagem de banco de dados - PostgreSQL mais especificamente.

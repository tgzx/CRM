package ProjetoCRM;

import java.sql.SQLException;
import java.util.Scanner;

public class MainBanco {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String enderecoIP = "192.168.1.9"; // Endereco do servidor (SQL) [localhost ou ip] pode ser alterado em "pg_hba.conf" meu hamachi 25.38.77.29 / meu ip 192.168.1.9
        boolean loop = false;
        BancoFuncao.TesteConexao(enderecoIP);
        System.out.println("Selecione uma das opções abaixo: ");
        while (!loop){
            BancoFuncao.Menu1();

            String respostaMenu1 = scanner.next(); System.out.println();
            if (respostaMenu1.equals("1")){
                BancoFuncao.Vender(enderecoIP);
            } else if (respostaMenu1.equals("2")){
                BancoFuncao.Cadastrar(enderecoIP);
            } else if (respostaMenu1.equals("3")){
                BancoFuncao.Menu2();
                String respostaMenu2 = scanner.next();
                if (respostaMenu2.equals("1")){
                    BancoFuncao.Consultar(enderecoIP, respostaMenu2);
                } else if (respostaMenu2.equals("2")){
                    BancoFuncao.Consultar(enderecoIP, respostaMenu2);
                } else if (respostaMenu2.equals("3")){
                    BancoFuncao.Consultar(enderecoIP, respostaMenu2);
                } else {
                    BancoFuncao.Consultar(enderecoIP, respostaMenu2);
                }
                loop = false;
            } else if (respostaMenu1.equals("4")){
                BancoFuncao.GerarRelatorio(enderecoIP);
            } else if (respostaMenu1.equals("5")){
                loop = true;
            } else {
                System.out.println("Selecione apenas uma das opcoes citadas.");
                loop = false;
            }
        }
    }
}
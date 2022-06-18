package ProjetoCRM;

import java.sql.SQLException;
import java.util.Scanner;

public class MainBanco {
    // Endereco do servidor (SQL) [localhost ou ip] pode ser alterado em "pg_hba.conf" meu hamachi 25.38.77.29 /
    // meu IPV4 192.168.1.9 / meu IPV6 fe80::5e0:23f9:68fc:329d / MySQL: remotemysql.com
    public static String enderecoIP = "remotemysql.com";
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean loop = false;

        while (!loop){
            BaseFunctions.Menu1();
            String respostaMenu1 = scanner.next(); System.out.println();
            if (respostaMenu1.equals("1")){
                BaseFunctions.Vender();
            } else if (respostaMenu1.equals("2")){
                BaseFunctions.Cadastrar();
            } else if (respostaMenu1.equals("3")){
                BaseFunctions.Menu2();
                String respostaMenu2 = scanner.next();
                if (respostaMenu2.equals("1")){
                    BaseFunctions.Listar(respostaMenu2);
                } else if (respostaMenu2.equals("2")){
                    BaseFunctions.Listar(respostaMenu2);
                } else if (respostaMenu2.equals("3")){
                    BaseFunctions.Listar(respostaMenu2);
                } else if (respostaMenu2.equals("4")){
                    BaseFunctions.Listar(respostaMenu2);
                }
                loop = false;
            } else if (respostaMenu1.equals("4")){
                BaseFunctions.Menu3();
                String respostaMenu3 = scanner.next();
                if (respostaMenu3.equals("1")) {
                    BaseFunctions.relatorioMovimentacao();
                } else if (respostaMenu3.equals("2")){
                    BaseFunctions.relatorioVenda();
                }
            }
            else if (respostaMenu1.equals("5")) {
                BaseFunctions.Atualizar();
            }else if (respostaMenu1.equals("6")){
                BaseFunctions.ZerarBanco();
                loop = false;
            } else if (respostaMenu1.equals("7")){
                loop = true;
            } else {
                System.out.println("Selecione apenas uma das opcoes citadas.");
                loop = false;
            }
        }
    }
}
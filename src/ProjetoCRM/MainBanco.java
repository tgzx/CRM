package ProjetoCRM;

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
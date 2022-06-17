package ProjetoCRM;

public class Espacamento {
    public static String larguraLimite(String texto){
        int cont = texto.length();
        while (cont < 10){
            for (int i = 0; i < (10 - texto.length()); i++) {
                if (i > 0){
                    texto = " " + texto;
                }
                texto = texto + " ";
            }
            cont++;
        }
        return texto;
    }

    public static String larguraLimiteEstoque(Float numero){
        String texto = String.valueOf(numero);
        int cont = 0;
        while (cont < 5){
            for (int i = 0; i < (5 - texto.length()); i++) {
                texto = texto + " ";
            }
            cont++;
        }
        return texto;
    }

    public static String larguraLimiteMedida(String texto){
        int cont = 0;
        while (cont < 5){
            for (int i = 0; i < (5 - texto.length()); i++) {
                texto = " " + texto;
            }
            cont++;
        }
        return texto;
    }
}

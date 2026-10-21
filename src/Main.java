import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner teclado = new Scanner(System.in)) {
            Random random = new Random();
            while (true) {
                System.out.println("Escolha sua cor: V (vermelho) ou A (azul). Você começa.");
                if (!teclado.hasNextLine()) return;
                String cor = teclado.nextLine().trim().toUpperCase(java.util.Locale.ROOT);
                if (!cor.equals("V") && !cor.equals("A")) { System.out.println("Cor inválida."); continue; }
                char humano = cor.charAt(0);
                Tabuleiro tabuleiro = new Tabuleiro(humano);
                while (!tabuleiro.terminou()) {
                    imprimir(tabuleiro);
                    if (tabuleiro.getJogador() == humano) {
                        System.out.println("Escolha uma coluna (1 a 7):");
                        if (!teclado.hasNextLine()) return;
                        String entrada = teclado.nextLine().trim();
                        try { tabuleiro.jogar(Integer.parseInt(entrada) - 1); }
                        catch (IllegalArgumentException e) { System.out.println("Jogada inválida: use uma coluna livre de 1 a 7."); }
                    } else {
                        System.out.println("Computador jogou na coluna " + (tabuleiro.jogarComputador(random) + 1));
                    }
                }
                imprimir(tabuleiro);
                System.out.println(tabuleiro.empatou() ? "A partida empatou!" : "Vencedor: " + tabuleiro.getVencedor());
                String resposta;
                do {
                    System.out.println("Jogar novamente? 1 - Sim | 2 - Não");
                    if (!teclado.hasNextLine()) return;
                    resposta = teclado.nextLine().trim();
                } while (!resposta.equals("1") && !resposta.equals("2"));
                if (resposta.equals("2")) return;
            }
        }
    }
    private static void imprimir(Tabuleiro tabuleiro) {
        for (int l = 0; l < Tabuleiro.LINHAS; l++) {
            for (int c = 0; c < Tabuleiro.COLUNAS; c++) {
                char cor = tabuleiro.getCasa(l, c);
                System.out.print("| " + (cor == 'B' ? '.' : cor) + " ");
            }
            System.out.println('|');
        }
        System.out.println("  1   2   3   4   5   6   7");
    }
}

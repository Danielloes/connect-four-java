import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Tabuleiro {
    public static final int LINHAS = 6;
    public static final int COLUNAS = 7;
    private final char[][] casas = new char[LINHAS][COLUNAS];
    private char jogador;
    private char vencedor;
    private int jogadas;

    public Tabuleiro(char primeiroJogador) { reiniciar(primeiroJogador); }
    public void reiniciar(char primeiroJogador) {
        if (primeiroJogador != 'V' && primeiroJogador != 'A') throw new IllegalArgumentException("Cor inválida.");
        for (char[] linha : casas) java.util.Arrays.fill(linha, 'B');
        jogador = primeiroJogador;
        vencedor = 'B';
        jogadas = 0;
    }
    public char getJogador() { return jogador; }
    public char getVencedor() { return vencedor; }
    public int getJogadas() { return jogadas; }
    public char getCasa(int linha, int coluna) { return casas[linha][coluna]; }
    public boolean empatou() { return jogadas == LINHAS * COLUNAS && vencedor == 'B'; }
    public boolean terminou() { return vencedor != 'B' || empatou(); }
    public List<Integer> colunasDisponiveis() {
        List<Integer> colunas = new ArrayList<>();
        if (!terminou()) for (int c = 0; c < COLUNAS; c++) if (casas[0][c] == 'B') colunas.add(c);
        return List.copyOf(colunas);
    }
    public void jogar(int coluna) {
        if (terminou()) throw new IllegalStateException("A partida já terminou.");
        if (coluna < 0 || coluna >= COLUNAS) throw new IllegalArgumentException("Escolha uma coluna de 1 a 7.");
        if (casas[0][coluna] != 'B') throw new IllegalArgumentException("Coluna cheia. Escolha outra.");
        int linha = LINHAS - 1;
        while (casas[linha][coluna] != 'B') linha--;
        casas[linha][coluna] = jogador;
        jogadas++;
        if (venceu(linha, coluna)) vencedor = jogador;
        if (!terminou()) jogador = jogador == 'V' ? 'A' : 'V';
    }
    public int jogarComputador(Random random) {
        List<Integer> disponiveis = colunasDisponiveis();
        if (disponiveis.isEmpty()) throw new IllegalStateException("Não há jogadas disponíveis.");
        int coluna = disponiveis.get(random.nextInt(disponiveis.size()));
        jogar(coluna);
        return coluna;
    }
    private boolean venceu(int linha, int coluna) {
        int[][] direcoes = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] d : direcoes) {
            if (1 + contar(linha, coluna, d[0], d[1]) + contar(linha, coluna, -d[0], -d[1]) >= 4) return true;
        }
        return false;
    }
    private int contar(int linha, int coluna, int dl, int dc) {
        int total = 0;
        for (int l = linha + dl, c = coluna + dc; l >= 0 && l < LINHAS && c >= 0 && c < COLUNAS
                && casas[l][c] == jogador; l += dl, c += dc) total++;
        return total;
    }
}

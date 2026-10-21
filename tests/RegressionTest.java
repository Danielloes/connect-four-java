import java.util.Random;
public class RegressionTest {

    private static int checks;
    private static void check(boolean condition) {
        checks++;
        if (!condition) throw new AssertionError("Verificação " + checks + " falhou.");
    }
    @FunctionalInterface private interface Action { void run() throws Exception; }
    private static void expect(Class<? extends Exception> type, Action action) throws Exception {
        try { action.run(); }
        catch (Exception e) {
            check(type.isInstance(e)); return;
        }
        throw new AssertionError("Esperada exceção " + type.getSimpleName());
    }

    private static void vitoria(int... colunas) throws Exception {
        Tabuleiro t = new Tabuleiro('V');
        for (int coluna : colunas) t.jogar(coluna);
        check(t.getVencedor() == 'V' && t.terminou() && !t.empatou());
        expect(IllegalStateException.class, () -> t.jogar(6));
        t.reiniciar('A');
        check(t.getJogadas() == 0 && t.getJogador() == 'A' && !t.terminou());
        for (int l = 0; l < 6; l++) for (int c = 0; c < 7; c++) check(t.getCasa(l,c) == 'B');
    }
    public static void main(String[] args) throws Exception {
        vitoria(0,6,1,6,2,5,3);
        vitoria(0,1,0,1,0,1,0);
        vitoria(0,1,1,2,4,2,2,3,4,3,5,3,3);
        vitoria(6,5,5,4,2,4,4,3,2,3,1,3,3);
        expect(IllegalArgumentException.class, () -> new Tabuleiro('X'));
        Tabuleiro t = new Tabuleiro('V');
        expect(IllegalArgumentException.class, () -> t.jogar(-1));
        expect(IllegalArgumentException.class, () -> t.jogar(7));
        check(t.getJogadas() == 0 && t.getJogador() == 'V');
        for (int i = 0; i < 6; i++) t.jogar(0);
        char vez = t.getJogador();
        expect(IllegalArgumentException.class, () -> t.jogar(0));
        check(t.getJogadas() == 6 && t.getJogador() == vez);
        check(!t.colunasDisponiveis().contains(0));
        check(t.jogarComputador(new Random(1)) != 0);
        check(t.getJogadas() == 7);
        Tabuleiro empate = new Tabuleiro('V');
        int[] sequencia = {5,5,4,4,5,5,0,3,4,3,5,3,4,2,4,6,2,2,2,4,6,5,0,6,3,1,0,0,6,3,3,6,0,0,2,1,2,6,1,1,1,1};
        for (int coluna : sequencia) empate.jogar(coluna);
        check(empate.getJogadas() == 42 && empate.empatou() && empate.terminou());
        expect(IllegalStateException.class, () -> empate.jogarComputador(new Random(2)));
        for (int seed = 0; seed < 500; seed++) {
            Tabuleiro partida = new Tabuleiro('V'); Random random = new Random(seed);
            while (!partida.terminou()) {
                int antes = partida.getJogadas();
                partida.jogarComputador(random);
                check(partida.getJogadas() == antes + 1 && partida.getJogadas() <= 42);
            }
            check(partida.getVencedor() != 'B' || partida.empatou());
        }
        System.out.println("Liga 4: " + checks + " verificações OK (inclui 500 partidas completas)");
    }

}

# Liga 4 em Java

Jogo de console com tabuleiro 6×7. O jogador humano escolhe V (vermelho) ou A (azul), começa a partida e enfrenta um adversário que escolhe aleatoriamente entre as colunas livres.

## Executar e testar

Requisitos: JDK 17 ou superior com `java` e `javac` disponíveis no PATH, e PowerShell. Sem bibliotecas externas.

Na pasta deste projeto:

```powershell
.\build.ps1 -Test
.\build.ps1 -Run
```

`-Test` compila fontes e testes com UTF-8, todos os avisos habilitados e avisos tratados como erro. Os testes usam um programa Java de regressão, sem depender de JUnit ou downloads. Os arquivos de teste ficam em `build/`, ignorado pelo Git.

Os parâmetros `-Test` e `-Run` também podem ser usados juntos. A pasta `src/` contém o programa e `tests/` contém as verificações. Cada pasta de projeto pode virar um repositório independente.

## Como jogar

Digite uma coluna de 1 a 7. As peças caem até a primeira posição disponível. Uma sequência de quatro peças na horizontal, vertical ou em qualquer diagonal vence a partida. Se as 42 posições forem preenchidas sem vencedor, há empate. Ao terminar, digite 1 para uma nova partida ou 2 para sair. Encerrar a entrada também encerra o programa.

O programa aceita e recusa entradas inválidas sem perder a vez. O computador não escolhe colunas cheias. Uma nova partida zera o tabuleiro e o contador.

## Estrutura e testes

`Tabuleiro` implementa as regras; `Main` cuida do console. Colunas na API Java começam em zero. `reiniciar` permite reutilizar o tabuleiro. Uma jogada recusada não muda a vez nem o contador.

A suíte verifica vitórias nas quatro direções, empate com 42 jogadas legais, reinício, coluna cheia, limites e 500 partidas completas com sementes fixas. O adversário é aleatório, sem estratégia de inteligência artificial.

Origem: revisão do projeto acadêmico `liga4`.

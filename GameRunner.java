package stormboundanalyzer;

public class GameRunner {
  public static void main(String[] args) throws Exception {
    GameRunner gr = new GameRunner();
    Game game = new Game(
      5,
      4,
      Faction.WINTER_PACT,
      6,
      Faction.TRIBES_OF_SHADOWFEN,
      7,
      true
    );
    System.out.println(game.toString());
    gr.playCard(game, game.getBottomPlayer(), new Position(4, 3), new GreenPrototypes(1));
    gr.playCard(game, game.getBottomPlayer(), new Position(3, 2), new GreenPrototypes(1));
    // gr.playCard(game, game.getBottomPlayer(), new Position(3, 3), new GreenPrototypes(1));
    gr.playCard(game, game.getBottomPlayer(), new Position(2, 3), new GreenPrototypes(1));
    gr.nextTurn(game);
    gr.playCard(game, game.getTopPlayer(), new Position(0, 2), new GreenPrototypes(1));
    gr.playCard(game, game.getTopPlayer(), new Position(1, 1), new GreenPrototypes(1));
    gr.nextTurn(game);
    gr.playCard(game, game.getBottomPlayer(), new Position(4, 1), new GreenPrototypes(1));
    gr.nextTurn(game);
    gr.nextTurn(game);
    gr.nextTurn(game);
    gr.nextTurn(game);
    gr.nextTurn(game);
    gr.nextTurn(game);
    gr.nextTurn(game);

  }
  public void nextTurn(Game game) {
    game.endTurn();
    game.startTurn();
    System.out.println(game.toString());
  }

  public void playCard(Game game, Player player, Position position, Card card) {
    if (card.canPlay(game, player, position)) {
      System.out.println(String.format("%s playing level %d %s at %s", player.getName(), card.getLevel(), card.getName(), position.toString()));
      card.play(game, player, position);
      System.out.println(game.toString());
    } else {
      System.out.println(String.format("%s unable to play level %d %s at %s", player.getName(), card.getLevel(), card.getName(), position.toString()));
    }
  }

}

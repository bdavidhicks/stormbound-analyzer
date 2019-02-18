package stormboundanalyzer;

class Game {
  Board board;
  Player bottomPlayer, topPlayer;
  boolean bottomPlayerGoesFirst;

  public Game(Board board, Faction bottomPlayerFaction, int bottomPlayerLevel,
      Faction topPlayerFaction, int topPlayerLevel, boolean bottomPlayerGoesFirst) throws Exception {
    this.board = board;
    this.bottomPlayer = new Player(
      bottomPlayerFaction,
      false,
      bottomPlayerLevel,
      bottomPlayerGoesFirst
    );
    this.topPlayer = new Player(
      topPlayerFaction,
      true,
      topPlayerLevel,
      !bottomPlayerGoesFirst
    );
  }

  public Board getBoard() {return this.board;}
  public Player getBottomPlayer() {return this.bottomPlayer;}
  public Player getTopPlayer() {return this.topPlayer;}
  public String toString() {
    return this.topPlayer.toString() + String.format("%n") + this.board.toString() + String.format("%n") + this.bottomPlayer.toString();
  }
}

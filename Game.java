package stormboundanalyzer;

public class Game {
  Board board;
  Player bottomPlayer, topPlayer;
  boolean bottomPlayerGoesFirst;
  int turnCounter;

  public Game(int boardHeight, int boardWidth, Faction bottomPlayerFaction, int bottomPlayerLevel,
      Faction topPlayerFaction, int topPlayerLevel, boolean bottomPlayerGoesFirst) throws Exception {
    this.board = new Board(boardHeight, boardWidth, this);
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
    this.turnCounter = 1;
    this.bottomPlayerGoesFirst = bottomPlayerGoesFirst;
  }

  public Board getBoard() {return this.board;}

  public Player getBottomPlayer() {return this.bottomPlayer;}

  public Player getTopPlayer() {return this.topPlayer;}

  public Player getActivePlayer() {
    if (this.bottomPlayerGoesFirst) {
      if (this.turnCounter % 2 == 1) {
        return this.bottomPlayer;
      } else {
        return this.topPlayer;
      }
    } else {
      if (this.turnCounter % 2 == 1) {
        return this.topPlayer;
      } else {
        return this.bottomPlayer;
      }
    }
  }

  public void startTurn() {
    this.board.startTurn(this.getActivePlayer());
  }

  public void endTurn() {
    this.getActivePlayer().fillMana(this.turnCounter / 2 + 4);
    this.turnCounter += 1;
  }

  public int getTurnCounter() {return turnCounter;}

  public Player getWinner() {
    return (this.topPlayer.getBase().getHealth() <= 0) ? bottomPlayer :
      (this.bottomPlayer.getBase().getHealth() <= 0) ? topPlayer : null;
  }

  public boolean isOver() {
    return this.getWinner() != null;
  }

  public String toString() {
    return ((this.topPlayer == this.getActivePlayer()) ? "-> ": "   ") +
      this.topPlayer.toString() + String.format("%n") +
      this.board.toString() + String.format("%n") +
      ((this.bottomPlayer == this.getActivePlayer()) ? "-> ": "   ") +
      this.bottomPlayer.toString() + String.format("%n");
  }
}

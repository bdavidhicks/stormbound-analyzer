package stormboundanalyzer;

class Game {
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
  }

  public Board getBoard() {return this.board;}

  public Player getBottomPlayer() {return this.bottomPlayer;}

  public Player getTopPlayer() {return this.topPlayer;}

  public Player getActivePlayer() {
    if (this.bottomPlayerGoesFirst) {
      if (this.turnCounter % 2 == 0) {
        return this.bottomPlayer;
      } else {
        return this.topPlayer;
      }
    } else {
      if (this.turnCounter % 2 == 0) {
        return this.topPlayer;
      } else {
        return this.bottomPlayer;
      }
    }
  }

  public void startTurn() {
    System.out.println(String.format("Start Turn %d: %s is the Active Player", this.turnCounter, this.getActivePlayer().getName()));
    this.board.startTurn(this.getActivePlayer());
  }

  public void endTurn() {
    // System.out.println(String.format("End Turn %d: %s is the Active Player", this.turnCounter, this.getActivePlayer().getName()));
    this.getActivePlayer().fillMana(this.turnCounter / 2 + 4);
    this.turnCounter += 1;
  }

  public Player getWinner() {
    return (this.topPlayer.getBase().getHealth() <= 0) ? bottomPlayer :
      (this.bottomPlayer.getBase().getHealth() <= 0) ? topPlayer : null;
  }

  public boolean isOver() {
    return this.getWinner() != null;
  }

  public String toString() {
    return this.topPlayer.toString() + String.format("%n") + this.board.toString() + String.format("%n") + this.bottomPlayer.toString() + String.format("%n");
  }
}

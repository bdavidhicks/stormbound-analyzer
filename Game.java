package com.stormboundanalyzer;

public class Game {
  Board board;
  Player bottomPlayer, topPlayer;
  boolean bottomPlayerGoesFirst;
  int turnCounter;

  public Game(int boardHeight, int boardWidth, Faction bottomPlayerFaction, int bottomPlayerBaseStrength,
      Faction topPlayerFaction, int topPlayerBaseStrength, boolean bottomPlayerGoesFirst) throws Exception {
    this.board = new Board(boardHeight, boardWidth, this);
    this.bottomPlayer = new Player(
      bottomPlayerFaction,
      false,
      bottomPlayerBaseStrength,
      bottomPlayerGoesFirst,
      this.board.getRows() - 1
    );
    this.topPlayer = new Player(
      topPlayerFaction,
      true,
      topPlayerBaseStrength,
      !bottomPlayerGoesFirst,
      1
    );
    this.turnCounter = 1;
    this.bottomPlayerGoesFirst = bottomPlayerGoesFirst;
  }

  public Board getBoard() {return this.board;}

  public Player getBottomPlayer() {return this.bottomPlayer;}

  public Player getTopPlayer() {return this.topPlayer;}

  public Player getOpponent(Player player) {
    if (player == this.getBottomPlayer()) {
      return this.getTopPlayer();
    }
    return this.getBottomPlayer();
  }

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
    this.getActivePlayer().fillHand();
    this.turnCounter += 1;
  }

  public int getTurnCounter() {return turnCounter;}

  public Player getWinner() {
    return (this.topPlayer.getPlayerBase().getCurrentStrength() <= 0) ? bottomPlayer :
      (this.bottomPlayer.getPlayerBase().getCurrentStrength() <= 0) ? topPlayer : null;
  }

  public boolean isOver() {
    return this.getWinner() != null;
  }

  public void end() throws RuntimeException {
    throw new RuntimeException("Game Over");
  }

  public String toString() {
    return ((this.topPlayer == this.getActivePlayer()) ? "-> ": "   ") +
      this.topPlayer.toString() + String.format("%n") +
      this.board.toString() + String.format("%n") +
      ((this.bottomPlayer == this.getActivePlayer()) ? "-> ": "   ") +
      this.bottomPlayer.toString() + String.format("%n") +
      this.getActivePlayer().getHand();
  }
}

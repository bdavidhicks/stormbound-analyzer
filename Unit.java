package com.stormboundanalyzer;

import java.util.Comparator;

class Unit extends Summon implements Comparable<Card> {
  int movement;

  public Unit(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength);
    this.movement = movement;
  }

  public Unit copyCard() throws Exception {
    return new Unit(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }

  public int getMovement() {return this.movement;}

  public boolean canPlay(Game game, Player player, Position position) {
    return super.canPlay(game, player, position);
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
    }
  }

  public void afterPlay(Game game, Player player, Position position) {
    super.afterPlay(game, player, position);
    Position currentPosition = position;
    int remainingMovement = this.getMovement();
    while (remainingMovement > 0 && this.getCurrentStrength() > 0) {
      Position nextAttackPosition = this.calcNextAttack(game, player, currentPosition);
      this.attack(game, player, currentPosition, nextAttackPosition);
      currentPosition = nextAttackPosition;
      remainingMovement -= 1;
    }
  }

  public void move(Game game, Player player, Position position, Position newPosition) {
    Board board = game.getBoard();
    if (newPosition == null) {
      board.remove(board.getTileAt(position));
    } else if (board.isTileEmptyAt(newPosition)) {
      Tile tile = board.getTileAt(position);
      tile.getPosition().moveRow(newPosition.getRow() - position.getRow());
      tile.getPosition().moveCol(newPosition.getCol() - position.getCol());
      // player.front_line = player.calc_front_line(board)
    }
  }
  public Position calcNextAttack(Game game, Player player, Position position) {
    Board board = game.getBoard();
    Position front = board.getPositionInFront(player, position);
    if (front == null || (front != null && !board.isTileEmptyAt(front) && board.getTileAt(front).getOwner() != player)) {
      return front;
    }
    Position east = board.getPositionEast(position);
    boolean eastHasEnemy = east != null && !board.isTileEmptyAt(east) && board.getTileAt(east).getOwner() != player;
    Position west = board.getPositionWest(position);
    boolean westHasEnemy = west != null && !board.isTileEmptyAt(west) && board.getTileAt(west).getOwner() != player;

    if (east != null && eastHasEnemy && west != null && westHasEnemy) {
      if (Math.abs((board.getCols() - 1) / 2 - east.getCol()) <= Math.abs((board.getCols() - 1) / 2 - west.getCol())) {
        return east;
      }
      return west;
    } else if (east != null && eastHasEnemy) {
      return east;
    } else if (west != null && westHasEnemy) {
      return west;
    }
    return front;
  }

  public void onAttack(Game game, Player player, Position attackPosition, Position defendPosition) { }

  public void attack(Game game, Player player, Position attackPosition, Position defendPosition) {
    Board board = game.getBoard();
    if (!board.isTileEmptyAt(attackPosition) && board.getTileAt(attackPosition).getSummon() instanceof Unit) {
      Unit attacker = (Unit)board.getTileAt(attackPosition).getSummon();
      if (defendPosition != null && board.isTileEmptyAt(defendPosition)) {
        attacker.move(game, player, attackPosition, defendPosition);
      } else if (defendPosition == null || (board.getTileAt(defendPosition).getOwner() != player)) {
        Summon defender = (defendPosition != null) ? board.getTileAt(defendPosition).getSummon() : (Summon)game.getOpponent(player).getPlayerBase();
        int removeStrength = Math.min(attacker.getCurrentStrength(), defender.getCurrentStrength());
        this.onAttack(game, player, attackPosition, defendPosition);
        defender.takeDamage(game, game.getOpponent(player), defendPosition, removeStrength);
        attacker.takeDamage(game, player, attackPosition, removeStrength);
        if (attacker.isAlive()) {
          attacker.move(game, player, attackPosition, defendPosition);
        }
      }
    }
  }

  @Override
  public int compareTo(Card c) {
    if (c instanceof Unit) {
      Unit u = (Unit)c;
      return (this.getCost() - u.getCost() == 0) ?
        (this.getName().compareTo(u.getName()) == 0) ?
          (this.getStartingStrength() - u.getStartingStrength() == 0) ?
            this.getMovement() - u.getMovement()
            :
            this.getStartingStrength() - u.getStartingStrength()
          :
          this.getName().compareTo(u.getName())
        :
        this.getCost() - u.getCost();
    } else {
      return super.compareTo(c);
    }
  }

  public String toString() {
    return String.format("(%2d) %-20s [%2d] move %d: %s", this.getCost(), this.getName(), this.getStartingStrength(), this.getMovement(), this.getText());
  }
}

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
    int remainingMovement = this.movement;
    while (remainingMovement > 0 && this.currentStrength > 0) {
      Position nextAttackPosition = this.calcNextAttack(game, player, currentPosition);
      if (nextAttackPosition != null) {
        this.onAttack(game, player, currentPosition, nextAttackPosition);
        this.attack(game, player, currentPosition, nextAttackPosition);
        currentPosition = nextAttackPosition;
      } else {
        Position positionInFront = game.getBoard().getPositionInFront(position, player);
        this.move(game, player, position, positionInFront);
      }
      remainingMovement -= 1;
    }
  }

  public void attackOrMoveAhead(Game game, Player player, Position position) {
    Position front = game.getBoard().getPositionInFront(position, player);
    if (front == null) {
      this.onAttack(game, player, position, front);
      this.move(game, player, position, front);
    } else if (front != null && !game.getBoard().isTileEmptyAt(front) && game.getBoard().getTileAt(front).getOwner() != player) {
      this.onAttack(game, player, position, front);
      this.attack(game, player, position, front);
    } else if (front != null && game.getBoard().isTileEmptyAt(front)) {
      this.move(game, player, position, front);
    }
  }

  public void move(Game game, Player player, Position position, Position newPosition) {
    Board board = game.getBoard();
    if (newPosition == null) {
      if (player == game.getBottomPlayer()) {
        game.getTopPlayer().getBase().takeDamage(this.currentStrength);
      } else {
        game.getBottomPlayer().getBase().takeDamage(this.currentStrength);
      }
      board.remove(board.getTileAt(position));
    } else {
      if (board.isTileEmptyAt(newPosition)) {
        Tile tile = board.getTileAt(position);
        tile.getPosition().moveRow(newPosition.getRow() - position.getRow());
        tile.getPosition().moveCol(newPosition.getCol() - position.getCol());
        // player.front_line = player.calc_front_line(board)
      }
    }
  }
  public Position calcNextAttack(Game game, Player player, Position position) {
    Board board = game.getBoard();
    Position front = board.getPositionInFront(position, player);
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
    return null;
  }

  public void onAttack(Game game, Player player, Position attackPosition, Position defendPosition) { }

  public void attack(Game game, Player player, Position attackPosition, Position defendPosition) {
    Board board = game.getBoard();
    Unit attacker = (Unit)board.getTileAt(attackPosition).getSummon();
    Summon defender = board.getTileAt(defendPosition).getSummon();
    int removeStrength = Math.min(attacker.getCurrentStrength(), defender.getCurrentStrength());
    // System.out.println(removeStrength);
    Player attackingPlayer = board.getTileAt(attackPosition).getOwner();
    Player defendingPlayer = board.getTileAt(defendPosition).getOwner();
    // print('att_sp.card.current_strength before = {}'.format(att_sp.card.current_strength))
    // print('def_sp.card.current_strength before = {}'.format(def_sp.card.current_strength))
    attacker.takeDamage(removeStrength);
    defender.takeDamage(removeStrength);
    // print('att_sp.card.current_strength after = {}'.format(att_sp.card.current_strength))
    // print('def_sp.card.current_strength after = {}'.format(def_sp.card.current_strength))
    if (!defender.isAlive()) {
      // print('defending card died')
      // # TODO: this might have to change if the order of effects is wrong
      defender.onDeath(game, defendingPlayer, defendPosition);
      board.remove(board.getTileAt(defendPosition));
      // print('front line before = {}'.format(def_player.front_line))
      // def_player.front_line = def_player.calc_front_line(board)
      // print('front line after = {}'.format(def_player.front_line))
    }
    if (!attacker.isAlive()) {
      // print('attacking card died')
      // # TODO: this might have to change if the order of effects is wrong
      attacker.onDeath(game, attackingPlayer, attackPosition);
      board.remove(board.getTileAt(attackPosition));
    } else {
      // print('att_sp.card = {}'.format(att_sp.card))
      attacker.move(game, attackingPlayer, attackPosition, defendPosition);
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

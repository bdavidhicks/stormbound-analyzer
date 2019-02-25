package com.stormboundanalyzer;

import java.util.Comparator;

public class Summon extends Card implements Comparable<Card> {
  int startingStrength, currentStrength;
  boolean poisoned, frozen;

  public Summon(String name, String text, int level, Faction faction, Rarity rarity, int cost, int startingStrength) throws Exception {
    super(name, text, level, faction, rarity, cost);
    this.startingStrength = startingStrength;
    this.currentStrength = startingStrength;
    this.frozen = false;
    this.poisoned = false;
  }

  public Summon copyCard() throws Exception {
    return new Summon(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength());
  }

  public boolean canPlay(Game game, Player player, Position position) {
    if (super.canPlay(game, player, position) &&
        game.getBoard().isTileEmptyAt(position) ) { //&&
        //player.is_behind_front_line(position)) {
      return true;
    } else {
      return false;
    }
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      game.getBoard().summon(this, player, position, true);
    }
  }

  public void afterPlay(Game game, Player player, Position position) { }


  public boolean isFrozen() { return this.frozen; }
  public void unfreeze() {this.frozen = false;}
  public boolean isAlive() { return this.currentStrength > 0; }
  public int getCurrentStrength() { return this.currentStrength; }
  public int getStartingStrength() { return this.startingStrength; }
  public boolean isPoisoned() { return this.poisoned; }
  public void takeDamage(Game game, Player player, Position position, int damage) throws RuntimeException {
    this.currentStrength -= damage;
    if (this.currentStrength <= 0) {
      this.onDeath(game, player, position);
    }
  }
  public void addStrength(int amount) { this.currentStrength += amount; }
  public void onTurnBegin(Game game, Player player, Position position) { }
  public void onDeath(Game game, Player player, Position position) throws RuntimeException {
    game.getBoard().remove(game.getBoard().getTileAt(position));
  }

  @Override
  public int compareTo(Card c) {
    if (c instanceof Summon) {
      Summon s = (Summon)c;
      return (this.getCost() - s.getCost() == 0) ?
        (this.getName().compareTo(s.getName()) == 0) ?
          this.getStartingStrength() - s.getStartingStrength()
          :
          this.getName().compareTo(s.getName())
        :
        this.getCost() - s.getCost();
    } else {
      return super.compareTo(c);
    }
  }

  public String toString() {
    return String.format("(%2d) %-20s [%2d]       : %s", this.getCost(), this.getName(), this.getStartingStrength(), this.getText());
  }
}

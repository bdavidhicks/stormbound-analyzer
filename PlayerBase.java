package com.stormboundanalyzer;

import java.util.Comparator;

public class PlayerBase extends Structure implements Comparable<Card> {

  private PlayerBase(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength) throws Exception {
    super(name, text, level, faction, rarity, cost, strength);
  }

  public PlayerBase(int health) throws Exception {
    super(
      "Player Base",
      "This is the player's base",
      1,
      Faction.NEUTRAL,
      Rarity.COMMON,
      0,
      health
    );
    if (health < 10 || health > 20) {
      System.out.format("Invalid PlayerBase health of %d given", health);
      throw new Exception("Invalid PlayerBase health");
    }
  }

  public PlayerBase copyCard() throws Exception {
    return new PlayerBase(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength());
  }

  public boolean canPlay(Game game, Player player, Position position) {
    return super.canPlay(game, player, position);
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
    }
  }

  public void onDeath(Game game, Player player, Position position) throws RuntimeException {
    game.end();
  }

  @Override
  public int compareTo(Card c) {
    return super.compareTo(c);
  }

  public String toString() {
    return String.format("(%2d) %-20s [%2d]       : %s", this.getCost(), this.getName(), this.getStartingStrength(), this.getText());
  }
}

package com.stormboundanalyzer;

import java.util.Comparator;

class Structure extends Summon implements Comparable<Card> {

  public Structure(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength) throws Exception {
    super(name, text, level, faction, rarity, cost, strength);
  }

  public Structure copyCard() throws Exception {
    return new Structure(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength());
  }

  public boolean canPlay(Game game, Player player, Position position) {
    return super.canPlay(game, player, position);
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
    }
  }

  @Override
  public int compareTo(Card c) {
    return super.compareTo(c);
  }

  public String toString() {
    return String.format("(%2d) %-20s [%2d]       : %s", this.getCost(), this.getName(), this.getStartingStrength(), this.getText());
  }
}

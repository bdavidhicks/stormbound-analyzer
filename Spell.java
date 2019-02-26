package com.stormboundanalyzer;

import java.util.Comparator;

public class Spell extends Card implements Comparable<Card> {

  public Spell(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    super(name, text, level, faction, rarity, cost);
  }

  public Spell copyCard() throws Exception {
    return new Spell(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost());
  }

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
    game.getBoard().setNewFrontLines();
  }

  @Override
  public int compareTo(Card c) {
    return super.compareTo(c);
  }

  public String toString() {
    return String.format("(%2d) %-20s            : %s", this.getCost(), this.getName(), this.getText());
  }
}

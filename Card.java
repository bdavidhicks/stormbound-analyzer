package com.stormboundanalyzer;

import java.util.Comparator;

public class Card implements Comparable<Card> {
  String name, text;
  int level, cost;
  Faction faction;
  Rarity rarity;

  public Card(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    this.name = name;
    this.text = text;
    if (level < 1 || level > 5) {
      throw new Exception("Card level must be between 1 and 5");
    }
    this.level = level;
    this.faction = faction;
    this.rarity = rarity;
    if (cost < 0) {
      throw new Exception("Card cost must be greater than or equal to 0");
    }
    this.cost = cost;
  }

  public Card copyCard() throws Exception {
    return new Card(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost());
  }

  public boolean canPlay(Game game, Player player, Position position) {
    if (player.getCurrentMana() < this.cost) {
      return false;
    } else {
      return true;
    }
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      player.spendMana(this.cost);
      player.discardCard(this, false);
    }
  }

  public void afterPlay(Game game, Player player, Position position) { }

  public String getName() { return this.name; }
  public int getLevel() { return this.level; }
  public String getText() { return this.text; }
  public int getCost() { return this.cost; }
  public Faction getFaction() { return this.faction; }
  public Rarity getRarity() { return this.rarity; }

  @Override
  public int compareTo(Card c) {
    return (this.getCost() - c.getCost() == 0) ? this.getName().compareTo(c.getName()) : this.getCost() - c.getCost();
  }

  public String toString() {
    return String.format("(%2d) %-20s            : %s", this.getCost(), this.getName(), this.getText());
  }
}

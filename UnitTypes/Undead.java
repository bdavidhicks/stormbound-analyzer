package com.stormboundanalyzer;

class Undead extends Unit {
  public Undead(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Undead copyCard() throws Exception {
    return new Undead(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}

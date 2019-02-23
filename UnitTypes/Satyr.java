package com.stormboundanalyzer;

class Satyr extends Unit {
  public Satyr(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Satyr copyCard() throws Exception {
    return new Satyr(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}

package com.stormboundanalyzer;

class Frostling extends Unit {
  public Frostling(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Frostling copyCard() throws Exception {
    return new Frostling(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}

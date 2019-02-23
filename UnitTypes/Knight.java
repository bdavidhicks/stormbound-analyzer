package com.stormboundanalyzer;

class Knight extends Unit {
  public Knight(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Knight copyCard() throws Exception {
    return new Knight(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}

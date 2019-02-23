package com.stormboundanalyzer;

class Hero extends Unit {
  public Hero(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Hero copyCard() throws Exception {
    return new Hero(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getRarity(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}

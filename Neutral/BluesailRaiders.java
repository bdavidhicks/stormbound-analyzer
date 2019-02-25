package com.stormboundanalyzer;

class BluesailRaiders extends Pirate {
  private BluesailRaiders(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public BluesailRaiders(Integer level) throws Exception {
    super(
      "Bluesail Raiders",
      "",
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      5,
      calcStrength(level.intValue()),
      2
    );
  }

  public BluesailRaiders copyCard() throws Exception {
    return new BluesailRaiders(this.getLevel());
  }

  private static int calcStrength(int level) {
    return level + 2;
  }
}

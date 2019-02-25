package com.stormboundanalyzer;

class WarfrontRunners extends Knight {
  private WarfrontRunners(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public WarfrontRunners(Integer level) throws Exception {
    super(
      "Warfront Runners",
      "",
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      4,
      calcStrength(level.intValue()),
      2
    );
  }

  public WarfrontRunners copyCard() throws Exception {
    return new WarfrontRunners(this.getLevel());
  }

  private static int calcStrength(int level) {
    return level + 1;
  }
}

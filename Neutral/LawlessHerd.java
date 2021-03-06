package com.stormboundanalyzer;

class LawlessHerd extends Satyr {
  private LawlessHerd(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public LawlessHerd(Integer level) throws Exception {
    super(
      "Lawless Herd",
      "",
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      2,
      calcStrength(level.intValue()),
      0
    );
  }

  private static int calcStrength(int level) {
    return level + 1;
  }

  public LawlessHerd copyCard() throws Exception {
    return new LawlessHerd(this.getLevel());
  }
}

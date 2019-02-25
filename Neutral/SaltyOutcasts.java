package com.stormboundanalyzer;

class SaltyOutcasts extends Toad {
  private SaltyOutcasts(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public SaltyOutcasts(Integer level) throws Exception {
    super(
      "Salty Outcasts",
      "",
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      7,
      calcStrength(level.intValue()),
      2
    );
  }

  public SaltyOutcasts copyCard() throws Exception {
    return new SaltyOutcasts(this.getLevel());
  }

  private static int calcStrength(int level) {
    return (level <= 4) ? level + 4 : level + 5;
  }
}

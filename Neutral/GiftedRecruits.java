package com.stormboundanalyzer;

class GiftedRecruits extends Knight {
  private GiftedRecruits(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public GiftedRecruits(Integer level) throws Exception {
    super(
      "Gifted Recruits",
      "",
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      2,
      level.intValue(),
      1
    );
  }

  public GiftedRecruits copyCard() throws Exception {
    return new GiftedRecruits(this.getLevel());
  }
}

package stormboundanalyzer;

class LawlessHerd extends Satyr {
  private LawlessHerd(String name, String text, int level, Faction faction, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, cost, strength, movement);
  }

  public LawlessHerd(int level) throws Exception {
    super(
      "Lawless Herd",
      "",
      level,
      Faction.NEUTRAL,
      2,
      1 + level,
      0
    );
  }

  public LawlessHerd copyCard() throws Exception {
    return new LawlessHerd(this.getLevel());
  }
}

package com.stormboundanalyzer;

class NorthseaDogs extends Pirate {
  private NorthseaDogs(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public NorthseaDogs(Integer level) throws Exception {
    super(
      "Northsea Dogs",
      String.format("When played as the last card in your hand, gain %d strength", calcGainStrength(level.intValue())),
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.RARE,
      2,
      1,
      0
    );
  }

  public NorthseaDogs copyCard() throws Exception {
    return new NorthseaDogs(this.getLevel());
  }

  private static int calcGainStrength(int level) {
    return (level == 5) ? 10 : level + 4;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      // If this is the last card played, add strength to it
      if (player.getHand().isEmpty()) {
        this.addStrength(calcGainStrength(this.getLevel()));
      }
    }
  }

}

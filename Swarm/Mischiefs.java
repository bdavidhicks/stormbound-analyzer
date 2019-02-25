package com.stormboundanalyzer;

class Mischiefs extends Undead {
  private Mischiefs(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Mischiefs(Integer level) throws Exception {
    super(
      "Mischiefs",
      String.format("On play, deal %d damage to the enemy base", calcDamage(level.intValue())),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.COMMON,
      4,
      calcStrength(level.intValue()),
      1
    );
  }

  public Mischiefs copyCard() throws Exception {
    return new Mischiefs(this.getLevel());
  }

  private static int calcStrength(int level) {
    return (level <= 3) ? level + 1 : level;
  }

  private static int calcDamage(int level) {
    return (level <= 3) ? 1 : 2;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      game.getOpponent(player).getPlayerBase().takeDamage(game, game.getOpponent(player), null, calcDamage(this.getLevel()));
    }
  }

}

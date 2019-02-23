package com.stormboundanalyzer;

class Mischiefs extends Undead {
  private Mischiefs(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Mischiefs(Integer level) throws Exception {
    super(
      "Mischiefs",
      String.format("On play, deal %d damage to the enemy base", (level.intValue() <= 3) ? 1 : 2),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.COMMON,
      4,
      (level.intValue() <= 3) ? level.intValue() + 1 : level.intValue(),
      1
    );
  }

  public Mischiefs copyCard() throws Exception {
    return new Mischiefs(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      game.getOpponent(player).getBase().takeDamage((this.level <= 3) ? 1 : 2);
    }
  }

}

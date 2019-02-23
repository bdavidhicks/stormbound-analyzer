package com.stormboundanalyzer;

public class HeraldsHymn extends Spell {
  public HeraldsHymn(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    super(name, text, level, faction, rarity, cost);
  }

  public HeraldsHymn(Integer level) throws Exception {
    super(
      "Herald's Hymn",
      String.format("Give %d strength to target friendly unit. Command all friendly units in its row forawrd", level.intValue()),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.COMMON,
      5
    );
  }

  public HeraldsHymn copyCard() throws Exception {
    return new HeraldsHymn(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
    }
  }
}

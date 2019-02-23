package com.stormboundanalyzer;

public class NeedleBlast extends Spell {
  public NeedleBlast(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    super(name, text, level, faction, rarity, cost);
  }

  public NeedleBlast(Integer level) throws Exception {
    super(
      "Needle Blast",
      String.format("Deal %d damage to %d random enemies", (level.intValue() - 1) / 2 + 2, level.intValue() / 2 + 2),
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      6
    );
  }

  public NeedleBlast copyCard() throws Exception {
    return new NeedleBlast(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
    }
  }
}

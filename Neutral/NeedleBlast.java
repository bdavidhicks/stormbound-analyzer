package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
      List<Tile> targets = game.getBoard().getAllTargets().stream()
        .filter(t -> t.getOwner() != player &&
          t.getSummon().isAlive())
        .collect(Collectors.toList());
      if (targets.size() > 0) {
        List<Tile> choices = Choice.chooseMany(targets, this.level / 2 + 2);
        for (Tile tile : choices) {
          Summon summon = tile.getSummon();
          summon.takeDamage(game, tile.getOwner(), tile.getPosition(), (this.level - 1) / 2 + 2);
        }
      }
    }
  }
}

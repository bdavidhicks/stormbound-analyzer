package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Felflares extends Frostling {
  private Felflares(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Felflares(Integer level) throws Exception {
    super(
      "Felflares",
      String.format("On play, deal %d damage to a random surrounding enemy", calcDamage(level.intValue())),
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      3,
      2,
      0
    );
  }

  public Felflares copyCard() throws Exception {
    return new Felflares(this.getLevel());
  }

  private static int calcDamage(int level) {
    return level + 1;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      // deal damagage to a random surrounding enemy
      Board board = game.getBoard();
      List<Tile> targets = board.getSurroundingPlayerTypeTargets(game.getOpponent(player), position, Summon.class).stream()
        .filter(t -> !(t.getSummon() instanceof PlayerBase))
        .collect(Collectors.toList());
      if (targets.size() > 0) {
        Tile tile = Choice.chooseOne(targets);
        tile.getSummon().takeDamage(game, tile.getOwner(), tile.getPosition(), calcDamage(this.getLevel()));
      }
    }
  }

}

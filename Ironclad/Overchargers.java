package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Overchargers extends Rodent {
  private Overchargers(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Overchargers(Integer level) throws Exception {
    super(
      "Overchargers",
      String.format("On play, deal %d damage to first enemy in front", calcDamage(level.intValue())),
      level.intValue(),
      Faction.IRONCLAD_UNION,
      Rarity.RARE,
      4,
      calcStrength(level.intValue()),
      1
    );
  }

  public Overchargers copyCard() throws Exception {
    return new Overchargers(this.getLevel());
  }

  private static int calcDamage(int level) {
    return (level - 1) / 2 + 1;
  }

  private static int calcStrength(int level) {
    return (level <= 2) ? level + 1 : level;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      // deal damage to first enemy in front
      Board board = game.getBoard();
      Position shotPosition = board.getPositionInFront(player, position);
      while (shotPosition != null && (board.isTileEmptyAt(shotPosition) || board.getTileAt(shotPosition).getOwner().equals(player))) {
        shotPosition = board.getPositionInFront(player, shotPosition);
      }
      if (shotPosition == null) {
        game.getOpponent(player).getPlayerBase().takeDamage(game, game.getOpponent(player), null, calcDamage(this.getLevel()));
      } else {
        Tile tile = board.getTileAt(shotPosition);
        Summon summon = tile.getSummon();
        summon.takeDamage(game, tile.getOwner(), tile.getPosition(), calcDamage(this.getLevel()));
      }
    }
  }
}

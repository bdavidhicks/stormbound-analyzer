package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HeraldsHymn extends Spell {
  public HeraldsHymn(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    super(name, text, level, faction, rarity, cost);
  }

  public HeraldsHymn(Integer level) throws Exception {
    super(
      "Herald's Hymn",
      String.format("Give %d strength to target friendly unit. Command all friendly units in its row forward", calcGainStrength(level.intValue())),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.COMMON,
      5
    );
  }

  public HeraldsHymn copyCard() throws Exception {
    return new HeraldsHymn(this.getLevel());
  }

  private static int calcGainStrength(int level) {
    return level;
  }

  public boolean canPlay(Game game, Player player, Position position) {
    Board board = game.getBoard();
    return super.canPlay(game, player, position) &&
      !board.isTileEmptyAt(position) &&
      board.getTileAt(position).getOwner() == player &&
      board.getTileAt(position).getSummon() instanceof Unit;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Board board = game.getBoard();
      Summon target = board.getTileAt(position).getSummon();
      target.addStrength(calcGainStrength(this.getLevel()));
      List<Tile> affected = board.getRowTiles(position.getRow()).stream()
        .filter(t -> t.getOwner() == player &&
          t.getSummon() instanceof Unit &&
          t.getSummon().isAlive())
        .collect(Collectors.toList());
      if (affected.size() > 0) {
        for (Tile tile : affected) {
          Unit unit = (Unit)tile.getSummon();
          unit.attack(game, tile.getOwner(), tile.getPosition(), board.getPositionInFront(tile.getOwner(), tile.getPosition()));
        }
      }
    }
  }
}

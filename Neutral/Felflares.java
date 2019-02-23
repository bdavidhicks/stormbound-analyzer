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
      String.format("On play, deal %d damage to a random surrounding enemy", level.intValue() + 1),
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

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      // deal damagage to a random surrounding enemy
      Board board = game.getBoard();
      List<Position> surrounding = board.getSurroundingList(position);
      List<Summon> targets = surrounding.stream()
        .filter(b -> !board.isTileEmptyAt(b) &&
          board.getTileAt(b).getOwner() != player &&
          board.getTileAt(b).getSummon() instanceof Summon &&
          board.getTileAt(b).getSummon().getCurrentStrength() > 0)
        .map(b -> board.getTileAt(b).getSummon())
        .collect(Collectors.toList());
      if (targets.size() > 0) {
        Summon choice = Choice.chooseOne(targets);
        choice.takeDamage(this.level + 1);
      }
    }
  }

}

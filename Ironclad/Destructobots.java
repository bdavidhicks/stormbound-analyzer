package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Destructobots extends Construct {
  private Destructobots(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Destructobots(Integer level) throws Exception {
    super(
      "Destructobots",
      "On play, deal 1 damage to another friendly unit",
      level.intValue(),
      Faction.IRONCLAD_UNION,
      Rarity.RARE,
      2,
      calcStrength(level.intValue()),
      1
    );
  }

  public Destructobots copyCard() throws Exception {
    return new Destructobots(this.getLevel());
  }

  private static int calcStrength(int level) {
    return level + 1;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      // deal 1 damage to random friendly unit
      Board board = game.getBoard();
      List<Position> targets = board.getOnBoardPlayerTypePositions(player, Unit.class).stream()
        .filter(p -> !board.getTileAt(p).getSummon().equals(this))
        .collect(Collectors.toList());
      if (targets.size() > 0) {
        Position choice = Choice.chooseOne(targets);
        Tile tile = board.getTileAt(choice);
        Summon summon = tile.getSummon();
        summon.takeDamage(game, tile.getOwner(), choice, 1);
      }
    }
  }
}

package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FiniteLoopers extends Construct {
  private FiniteLoopers(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public FiniteLoopers(Integer level) throws Exception {
    super(
      "Finite Loopers",
      String.format("On death, spawn a %d strength Construct on a random bordering tile", calcSpawnStrength(level.intValue())),
      level.intValue(),
      Faction.IRONCLAD_UNION,
      Rarity.COMMON,
      2,
      calcStrength(level.intValue()),
      1
    );
  }

  public FiniteLoopers copyCard() throws Exception {
    return new FiniteLoopers(this.getLevel());
  }

  private static int calcSpawnStrength(int level) {
    return (level - 1) / 2 + 1;
  }

  private static int calcStrength(int level) {
    return level / 2 + 2;
  }

  public void onDeath(Game game, Player player, Position position) {
    super.onDeath(game, player, position);
    // spawn a Construct on a random bordering tile
    Board board = game.getBoard();
    List<Position> targets = board.getBorderingEmptyPositions(position);
    if (targets.size() > 0) {
      Position choice = Choice.chooseOne(targets);
      try {
        board.summon(
          new Construct(
            "Construct",
            "",
            this.level,
            Faction.IRONCLAD_UNION,
            Rarity.COMMON,
            0,
            calcSpawnStrength(this.getLevel()),
            0
          ),
          player,
          choice,
          false
        );
      } catch (Exception ex) {
        System.out.println("Unexpected Exception caught in FiniteLoopers.onDeath");
        System.out.println(ex.toString());
      }
    }
  }
}

package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ShadyGhoul extends Undead {
  private ShadyGhoul(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public ShadyGhoul(Integer level) throws Exception {
    super(
      "Shady Ghoul",
      String.format("On death, spawn a %d strength Satyr on a random bordering tile", level.intValue()),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.RARE,
      3,
      1,
      2
    );
  }

  public ShadyGhoul copyCard() throws Exception {
    return new ShadyGhoul(this.getLevel());
  }

  public void onDeath(Game game, Player player, Position position) {
    super.onDeath(game, player, position);
    Board board = game.getBoard();
    List<Position> bordering = board.getBorderingList(position);
    List<Position> targets = bordering.stream()
      .filter(b -> board.isTileEmptyAt(b))
      .collect(Collectors.toList());
    if (targets.size() > 0) {
      Position choice = Choice.chooseOne(targets);
      try {
        board.summon(
          new Satyr(
            "Satyr",
            "",
            this.level,
            Faction.SWARM_OF_THE_EAST,
            Rarity.COMMON,
            0,
            this.level,
            0
          ),
          player,
          choice,
          false
        );
      } catch (Exception ex) {
        System.out.println("Unexpected Exception caught in ShadyGhoul.onDeath");
        System.out.println(ex.toString());
      }
    }
  }

}

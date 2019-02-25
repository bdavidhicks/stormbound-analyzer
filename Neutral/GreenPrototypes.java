package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GreenPrototypes extends Construct {
  private GreenPrototypes(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public GreenPrototypes(Integer level) throws Exception {
    super(
      "Green Prototypes",
      String.format("On death, give %d strength to a random bordering ENEMY unit", level),
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.RARE,
      1,
      level.intValue(),
      1
    );
  }

  public GreenPrototypes copyCard() throws Exception {
    return new GreenPrototypes(this.getLevel());
  }

  public void onDeath(Game game, Player player, Position position) {
    super.onDeath(game, player, position);
    // give a random boarding ENEMY unit strength equal to level
    Board board = game.getBoard();
    List<Position> bordering = board.getBorderingList(position);
    List<Summon> targets = bordering.stream()
      .filter(p -> !board.isTileEmptyAt(p) &&
        board.getTileAt(p).getOwner() != player &&
        board.getTileAt(p).getSummon() instanceof Unit &&
        board.getTileAt(p).getSummon().getCurrentStrength() > 0)
      .map(p -> board.getTileAt(p).getSummon())
      .collect(Collectors.toList());
    if (targets.size() > 0) {
      Summon choice = Choice.chooseOne(targets);
      choice.addStrength(this.level);
    }
  }
}

package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ForgottenSouls extends Undead {
  private ForgottenSouls(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public ForgottenSouls(Integer level) throws Exception {
    super(
      "Forgotten Souls",
      "On play, command a random bordering friendly unit forward",
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.RARE,
      3,
      calcStrength(level.intValue()),
      1
    );
  }

  public ForgottenSouls copyCard() throws Exception {
    return new ForgottenSouls(this.getLevel());
  }

  private static int calcStrength(int level) {
    return level / 2 + 1;
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Board board = game.getBoard();
      List<Tile> targets = board.getBorderingPlayerTypeTargets(player, position, Unit.class);
      if (targets.size() > 0) {
        Tile choice = Choice.chooseOne(targets);
        Position positionInFront = board.getPositionInFront(player, choice.getPosition());
        Unit unit = (Unit)choice.getSummon();
        unit.attack(game, player, choice.getPosition(), positionInFront);
      }
    }
  }

}

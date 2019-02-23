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
      1 + level.intValue() / 2,
      1
    );
  }

  public ForgottenSouls copyCard() throws Exception {
    return new ForgottenSouls(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Board board = game.getBoard();
      List<Position> bordering = board.getBorderingList(position);
      List<Tile> targets = bordering.stream()
        .filter(b -> !board.isTileEmptyAt(b) &&
          board.getTileAt(b).getOwner() == player &&
          board.getTileAt(b).getSummon() instanceof Unit &&
          board.getTileAt(b).getSummon().getCurrentStrength() > 0)
        .map(b -> board.getTileAt(b))
        .collect(Collectors.toList());
      if (targets.size() > 0) {
        Tile choice = Choice.chooseOne(targets);
        Position positionInFront = board.getPositionInFront(choice.getPosition(), player);
        if (positionInFront == null || board.isTileEmptyAt(positionInFront)) {
          Unit unit = (Unit)choice.getSummon();
          unit.move(game, player, choice.getPosition(), positionInFront);
        }
      }
    }
  }

}

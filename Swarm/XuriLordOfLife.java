package com.stormboundanalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class XuriLordOfLife extends DragonHero {
  private XuriLordOfLife(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public XuriLordOfLife(Integer level) throws Exception {
    super(
      "Xuri, Lord of Life",
      String.format("On play, fly to the first empty tile in front. Give %d strength to all friendly units passed", (level.intValue() - 1) / 2 + 2),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.LEGENDARY,
      5,
      (level.intValue() <= 2) ? (level.intValue() * 2) + 2 : level.intValue() * 2,
      0
    );
  }

  public XuriLordOfLife copyCard() throws Exception {
    return new XuriLordOfLife(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Board board = game.getBoard();
      List<Position> passed = new ArrayList<Position>();
      Position positionInFront = board.getPositionInFront(position, player);
      while (positionInFront != null && !board.isTileEmptyAt(positionInFront)) {
        passed.add(positionInFront);
        positionInFront = board.getPositionInFront(positionInFront, player);
      }
      if (positionInFront != null) {
        this.move(game, player, position, positionInFront);
        passed.stream()
          .map(p -> board.getTileAt(p))
          .filter(t -> t.getOwner() == player &&
            t.getSummon() instanceof Unit &&
            t.getSummon().getCurrentStrength() > 0)
          .forEach(t -> t.getSummon().addStrength((this.level - 1) / 2 + 2));
      }
    }
  }

}

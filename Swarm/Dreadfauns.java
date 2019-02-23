package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Dreadfauns extends Satyr {
  private Dreadfauns(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Dreadfauns(Integer level) throws Exception {
    super(
      "Dreadfauns",
      String.format("On play, spawn 2 Satyrs with %d strength on random bordering tiles", (level.intValue() - 1) / 2 + 2),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.COMMON,
      5,
      (level.intValue() <= 2) ? level.intValue() + 2 : level.intValue() + 1,
      0
    );
  }

  public Dreadfauns copyCard() throws Exception {
    return new Dreadfauns(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Board board = game.getBoard();
      List<Position> bordering = board.getBorderingList(position);
      List<Position> targets = bordering.stream()
        .filter(b -> board.isTileEmptyAt(b))
        .collect(Collectors.toList());
      List<Position> choices = Choice.chooseMany(targets, 2);
      for (Position spawnPosition : choices) {
        if (spawnPosition != null && board.isTileEmptyAt(spawnPosition)) {
          try {
            board.summon(
              new Satyr(
                "Satyr",
                "",
                this.level,
                Faction.SWARM_OF_THE_EAST,
                Rarity.COMMON,
                0,
                (this.level - 1) / 2 + 2,
                0
              ),
              player,
              spawnPosition,
              false
            );
          } catch (Exception ex) {
            System.out.println("Unexpected Exception caught in Dreadfauns.play");
            System.out.println(ex.toString());
          }
        }
      }
    }
  }

}

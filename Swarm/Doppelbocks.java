package com.stormboundanalyzer;

class Doppelbocks extends Satyr {
  private Doppelbocks(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public Doppelbocks(Integer level) throws Exception {
    super(
      "Doppelbocks",
      String.format("On play, spawn a %d strength Satyr on the tile in front", (level.intValue() - 1) / 2 + 1),
      level.intValue(),
      Faction.SWARM_OF_THE_EAST,
      Rarity.COMMON,
      2,
      1 + level.intValue() / 2,
      0
    );
  }

  public Doppelbocks copyCard() throws Exception {
    return new Doppelbocks(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Board board = game.getBoard();
      Position positionInFront = board.getPositionInFront(player, position);
      if (positionInFront != null && board.isTileEmptyAt(positionInFront)) {
        try {
          board.summon(
            new Satyr(
              "Satyr",
              "",
              this.level,
              Faction.SWARM_OF_THE_EAST,
              Rarity.COMMON,
              0,
              (this.level - 1) / 2 + 1,
              0
            ),
            player,
            positionInFront,
            false
          );
        } catch (Exception ex) {
          System.out.println("Unexpected Exception caught in Doppelbocks.play");
          System.out.println(ex.toString());
        }
      }
    }
  }

}

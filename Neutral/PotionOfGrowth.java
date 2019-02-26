package com.stormboundanalyzer;

import java.util.List;

public class PotionOfGrowth extends Spell {
  public PotionOfGrowth(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    super(name, text, level, faction, rarity, cost);
  }

  public PotionOfGrowth(Integer level) throws Exception {
    super(
      "Potion of Growth",
      String.format("Give %d strength to a target friendly unit", calcGainStrength(level.intValue())),
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      3
    );
  }

  public PotionOfGrowth copyCard() throws Exception {
    return new PotionOfGrowth(this.getLevel());
  }

  private static int calcGainStrength(int level) {
    return level + 2;
  }

  public boolean canPlay(Game game, Player player, Position position) {
    Board board = game.getBoard();
    return super.canPlay(game, player, position) &&
      !board.isTileEmptyAt(position) &&
      board.getTileAt(position).getOwner().equals(player) &&
      board.getTileAt(position).getSummon() instanceof Unit &&
      board.getTileAt(position).getSummon().isAlive();
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Summon summon = game.getBoard().getTileAt(position).getSummon();
      summon.addStrength(calcGainStrength(this.getLevel()));
    }
  }

  public List<Position> getPossiblePlayPositions(Game game, Player player) {
    return game.getBoard().getOnBoardPlayerTypePositions(player, Unit.class);
  }
}

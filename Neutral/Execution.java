package com.stormboundanalyzer;

public class Execution extends Spell {
  public Execution(String name, String text, int level, Faction faction, Rarity rarity, int cost) throws Exception {
    super(name, text, level, faction, rarity, cost);
  }

  public Execution(Integer level) throws Exception {
    super(
      "Execution",
      String.format("Deal %d damage to a target enemy unit or structure", calcDamage(level.intValue())),
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      4
    );
  }

  public Execution copyCard() throws Exception {
    return new Execution(this.getLevel());
  }

  private static int calcDamage(int level) {
    return level + 2;
  }

  public boolean canPlay(Game game, Player player, Position position) {
    Board board = game.getBoard();
    return super.canPlay(game, player, position) &&
      !board.isTileEmptyAt(position) &&
      !board.getTileAt(position).getOwner().equals(player) &&
      (
        board.getTileAt(position).getSummon() instanceof Unit ||
        (
          board.getTileAt(position).getSummon() instanceof Structure &&
          !(board.getTileAt(position).getSummon() instanceof PlayerBase)
        )
      ) &&
      board.getTileAt(position).getSummon().isAlive();
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      Tile tile = game.getBoard().getTileAt(position);
      Summon summon = tile.getSummon();
      summon.takeDamage(game, tile.getOwner(), tile.getPosition(), calcDamage(this.getLevel()));
    }
  }
}
